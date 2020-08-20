package com.example.order.controller;

import com.example.order.VO.ResultVO;
import com.example.order.client.ProductClient;
import com.example.order.common.DescreaseStockInput;
import com.example.order.common.ProductInfoOutput;
import com.example.order.converter.OrderForm2OrderDTOConverter;
import com.example.order.dto.OrderDTO;
import com.example.order.enums.ResultEnum;
import com.example.order.excetion.SellException;
import com.example.order.form.OrderForm;
import com.example.order.service.OrderService;
import com.example.order.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
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
        productClient.decreaseStock(Arrays.asList(new DescreaseStockInput("123456",2),
                        new DescreaseStockInput("123457",2)));
        return "productDecreaseStock ok";
    }

    @GetMapping("/productIncreaseStock")
    public String productIncreaseStock() {
        productClient.increaseStock(Arrays.asList(new DescreaseStockInput("123456",2),
                new DescreaseStockInput("123457",2)));
        return "productIncreaseStock ok";
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
