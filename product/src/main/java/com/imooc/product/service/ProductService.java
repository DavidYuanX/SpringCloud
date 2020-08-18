package com.imooc.product.service;

import com.imooc.product.dataobject.ProductInfo;
//import com.imooc.dto.CartDTO;
import com.imooc.product.dto.CartDTO;
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
    List<ProductInfo> findList(List<String> productIdList);


    // 保存
    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);

//    ProductInfo OnSale(String productId);
//
//    ProductInfo OffSale(String productId);
}
