package com.imooc.product.service;

import com.imooc.product.common.DescreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    // 查询所有在架商品
    List<ProductInfo> findUpAll();

    // 查询所有
//    Page<ProductInfo> findAll(Pageable pageable);


    // 查询商品列表
    List<ProductInfoOutput> findList(List<String> productInfoOutputList);


    // 保存
    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<DescreaseStockInput> descreaseStockInputList);

    // 减库存
    void decreaseStock(List<DescreaseStockInput> descreaseStockInputList);

//    ProductInfo OnSale(String productId);
//
//    ProductInfo OffSale(String productId);
}
