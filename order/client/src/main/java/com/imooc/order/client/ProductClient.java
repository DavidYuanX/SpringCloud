package com.imooc.order.client;

import com.imooc.product.common.DescreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
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
    Void decreaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);

    @PostMapping("/product/increaseStock")
    Void increaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);
}

