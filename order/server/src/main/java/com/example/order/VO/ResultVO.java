package com.example.order.VO;

import lombok.Data;

@Data
public class ResultVO<T> {

    // 错误码
    private Integer code;

    // 提示信息
    private String msg;

    // 返回内容
    private T data;
}
