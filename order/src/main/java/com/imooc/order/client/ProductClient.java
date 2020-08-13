package com.imooc.order.client;

import com.imooc.order.dataobject.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


// 微服务 Eureka  调用 其他服务
@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/")
    String productMsg();

    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(List<String> productIdList);
}
