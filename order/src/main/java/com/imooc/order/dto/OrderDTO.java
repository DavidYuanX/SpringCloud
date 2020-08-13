package com.imooc.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.order.dataobject.OrderDetail;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.PayStatusEnum;
//import com.imooc.utils.EnumUtil;
//import com.imooc.utils.seriallzer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) 去除当前类 的null
public class OrderDTO {

    // 订单Id
    private String orderId;

    // 买家名字
    private String buyerName;

    // 买家手机号
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 买家微信OpenId
    private String buyerOpenid;

    // 订单金额
    private BigDecimal orderAmount;

    // 订单状态
    private Integer orderStatus;

    // 支付状态
    private Integer payStatus;

//    // 创建时间
//    @JsonSerialize(using = Date2LongSerializer.class)
//    private Date createTime;
//
//    // 更新时间
//    @JsonSerialize(using = Date2LongSerializer.class)
//    private Date updateTime;

    private List<OrderDetail> orderDetailList;

//    @JsonIgnore
//    public OrderStatusEnum getOrderStatusEnum(){
//        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
//    }
//
//    @JsonIgnore
//    public PayStatusEnum getPayStatusEnum(){
//        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
//    }
//            = new ArrayList<>(); // 初始 数组
}
