package com.imooc.product.service;

import com.imooc.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() throws Exception{
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void findList() throws Exception{
        List<ProductInfo> list = productService.findList(Arrays.asList("123456", "123457"));
        Assert.assertTrue(list.isEmpty());
    }
}