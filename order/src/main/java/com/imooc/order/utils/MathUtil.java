package com.imooc.order.utils;

public class MathUtil {

    public static Boolean equals(Double d1,Double d2){
        double result = Math.abs(d1 - d2);
         return result < 0.01;
    }
}
