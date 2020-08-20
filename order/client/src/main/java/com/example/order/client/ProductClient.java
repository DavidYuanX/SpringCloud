package com.example.order.client;

import com.example.order.common.DescreaseStockInput;
import com.example.order.common.ProductInfoOutput;
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
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    void decreaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);

    @PostMapping("/product/increaseStock")
    void increaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);
}

