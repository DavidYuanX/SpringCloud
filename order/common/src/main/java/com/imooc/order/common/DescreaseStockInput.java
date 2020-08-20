package com.imooc.order.common;

import lombok.Data;

@Data
public class DescreaseStockInput {
    private String productId;

    private  Integer productQuantity;

    public DescreaseStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
