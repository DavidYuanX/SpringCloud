package com.imooc.product.service.impl;

import com.imooc.product.common.DescreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.enums.ResultEnum;
import com.imooc.product.excetion.SellException;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return repository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e,output);
                    return output;
                }).collect(Collectors.toList());
    }

//    @Override
//    public Page<ProductInfo> findAll(Pageable pageable) {
//        return repository.findAll(pageable);
//    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<DescreaseStockInput> descreaseStockInputList) {
        for (DescreaseStockInput descreaseStockInput: descreaseStockInputList) {
            ProductInfo productInfo = repository.findById(descreaseStockInput.getProductId()).orElse(null);
            if(productInfo == null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + descreaseStockInput.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<DescreaseStockInput> descreaseStockInputList) {
        for (DescreaseStockInput descreaseStockInput: descreaseStockInputList){
            ProductInfo productInfo = repository.findById(descreaseStockInput.getProductId()).orElse(null);
            if(productInfo == null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - descreaseStockInput.getProductQuantity();

            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

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
