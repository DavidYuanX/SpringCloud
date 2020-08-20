package com.example.product.cliect;

import com.example.product.common.DescreaseStockInput;
import com.example.product.common.ProductInfoOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {
    @Autowired

    @PostMapping("/product/listForOrder")
    List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);

    @PostMapping("/product/decreaseStock")
    Void decreaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);

    @PostMapping("/product/increaseStock")
    Void increaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList);
}
