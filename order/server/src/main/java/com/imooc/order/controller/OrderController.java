package com.imooc.order.controller;

import com.imooc.order.VO.ResultVO;
import com.imooc.order.client.ProductClient;
import com.imooc.order.converter.OrderForm2OrderDTOConverter;
import com.imooc.order.dto.OrderDTO;
import com.imooc.order.enums.ResultEnum;
import com.imooc.order.excetion.SellException;
import com.imooc.order.form.OrderForm;
import com.imooc.order.service.OrderService;
import com.imooc.order.utils.ResultVOUtil;
import com.imooc.product.common.DescreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/order")
@Slf4j
@RefreshScope
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductClient productClient;

    @Value("${env}")
    private String env;

    @GetMapping("/getPrint")
    public String getPrint(){

        return "env"; }

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        return productClient.productMsg();
    }

    @GetMapping("/getProductList")
    public List<ProductInfoOutput> getProductList() {
        return productClient.listForOrder(Arrays.asList("123456", "123457"));
    }

    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock() {
        productClient.decreaseStock(Arrays.asList(new DescreaseStockInput("123456",2)));
        return "ok";
    }

    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.error("创建订单 参数不正确");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        // orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("创建订单 购物车信息为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.caeate(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);
    }
}
