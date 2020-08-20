package com.example.order.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail {

    // 详情ID
    @Id
    private String detailId;

    // 关联订单Id
    private String orderId;

    // 商品id
    private String productId;

    // 商品名称
    private String productName;

    // 商品单价
    private BigDecimal productPrice;

    // 商品数量
    private Integer productQuantity;

    // 商品小图
    private String productIcon;
}
