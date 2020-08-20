package com.imooc.order.message;


import com.fasterxml.jackson.core.type.TypeReference;
import com.imooc.order.common.ProductInfoOutput;
import com.imooc.order.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoReceiver {

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        // message -> ProductInfoInfoOutput
        log.info("从队列{}接到消息:{}","productInfoOutputList",message);
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>)JsonUtil.fromJson(message,
                new TypeReference<List<ProductInfoOutput>>() {});
        for (ProductInfoOutput productInfoOutput:productInfoOutputList){
            log.info("从队列{}接到消息:{}","productInfoOutput",productInfoOutput);
            //  存储在redis
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),
                    String.valueOf(productInfoOutput.getProductStock()));
        }

    }
}
