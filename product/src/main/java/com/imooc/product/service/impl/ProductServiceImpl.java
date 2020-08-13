package com.imooc.product.service.impl;

import com.imooc.product.dataobject.ProductInfo;
//import com.imooc.dto.CartDTO;
import com.imooc.product.enums.ProductStatusEnum;
//import com.imooc.product.excetion.SellException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList);
    }

//    @Override
//    public Page<ProductInfo> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
//    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

//    @Override
//    @Transactional
//    public void increaseStock(List<CartDTO> cartDTOList) {
//        for (CartDTO cartDTO: cartDTOList) {
//            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
//            if(productInfo == null){
//                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//            }
//            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
//            productInfo.setProductStock(result);
//            repository.save(productInfo);
//        }
//    }

//    @Override
//    @Transactional
//    public void decreaseStock(List<CartDTO> cartDTOList) {
//        for (CartDTO cartDTO: cartDTOList){
//            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
//            if(productInfo == null){
//                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//            }
//
//            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
//
//            if(result < 0){
//                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
//            }
//
//            productInfo.setProductStock(result);
//            repository.save(productInfo);
//        }
//    }

//    @Override
//    public ProductInfo OnSale(String productId) {
//        ProductInfo productInfo = repository.findById(productId).orElse(null);
//        if(productInfo == null){
//            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//        }
//        if(productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
//            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
//        }
//
//        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
//
//        return repository.save(productInfo);
//    }
//
//    @Override
//    public ProductInfo OffSale(String productId) {
//        ProductInfo productInfo = repository.findById(productId).orElse(null);
//
//        if(productInfo == null){
//            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//        }
//        if(productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
//            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
//        }
//
//        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
//
//        return repository.save(productInfo);
//    }
}
