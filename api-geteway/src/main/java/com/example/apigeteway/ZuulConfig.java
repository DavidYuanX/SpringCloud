//package com.example.apigeteway;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
//import org.springframework.stereotype.Component;
// 动态加载配置
//@Component
//public class ZuulConfig {
//
//    @ConfigurationProperties("zuul")
//    @RefreshScope
//    public ZuulProperties zuulProperties(){
//        return new ZuulProperties();
//    }
//}
