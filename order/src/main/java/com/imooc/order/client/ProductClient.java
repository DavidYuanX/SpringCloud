package com.imooc.order.client;

import com.imooc.order.dataobject.ProductInfo;
import com.imooc.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


// 微服务 Eureka  调用 其他服务
@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    public String decreaseStock(@RequestBody List<CartDTO> cartDTOList);

    @PostMapping("/product/increaseStock")
    public String increaseStock(@RequestBody List<CartDTO> cartDTOList);
}

