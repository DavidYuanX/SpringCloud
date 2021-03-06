package com.example.order.service.impl;

import com.example.order.client.ProductClient;
import com.example.order.common.DescreaseStockInput;
import com.example.order.common.ProductInfoOutput;
import com.example.order.dataobject.OrderDetail;
import com.example.order.dataobject.OrderMaster;
import com.example.order.dto.OrderDTO;
import com.example.order.enums.OrderStatusEnum;
import com.example.order.enums.PayStatusEnum;
import com.example.order.enums.ResultEnum;
import com.example.order.excetion.SellException;
import com.example.order.repository.OrderDetailRepository;
import com.example.order.repository.OrderMasterRepository;
import com.example.order.service.OrderService;
import com.example.order.utils.KeyUtil;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    @Transactional
    public OrderDTO caeate(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();

        // 查询商品信息
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);

        // 计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for (ProductInfoOutput productInfo: productInfoList){
                if(productInfo.getProductId().equals(orderDetail.getProductId())){
                    // 单价 * 数量
                    orderAmout = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    // 订单详情 入库
                    orderDetailRepository.save(orderDetail);
                }
            }
            // 单价*数量

        }

        // 口库存
        List<DescreaseStockInput> descreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DescreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(descreaseStockInputList);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount (orderAmout);
        orderMaster.setOrderStatus (OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus (PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 查询订单
        Optional<OrderMaster> optionalOrderMaster = orderMasterRepository.findById(orderId);

        Boolean OrderIs = optionalOrderMaster.isPresent();

        if(!OrderIs){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断状态
        OrderMaster orderMaster = optionalOrderMaster.get();
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}