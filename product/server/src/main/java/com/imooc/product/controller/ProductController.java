package com.imooc.product.controller;


import com.imooc.product.VO.PrductVo;
import com.imooc.product.VO.ProductInfoVO;
import com.imooc.product.VO.ResultVO;
import com.imooc.product.common.DescreaseStockInput;
import com.imooc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductCategory;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.service.CategoryService;
import com.imooc.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO<PrductVo> list(){
        // 1. 查询所有在驾的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 2. 获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        // 3. 从数据库查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 4. 构造数据
        List<PrductVo> prductVoList = new ArrayList<>();
        for (ProductCategory productCategory: categoryList){
            PrductVo prductVo = new PrductVo();
            prductVo.setCategoryName(productCategory.getCategoryName());
            prductVo.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo: productInfoList){
                if(productInfo.getCategoryType().equals(prductVo.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            prductVo.setProductInfoVOList(productInfoVOList);
            prductVoList.add(prductVo);
        }
        ResultVO resultVO = new ResultVO();
        return resultVO.success(prductVoList);
    }

//    @PostMapping("/listForOrder")
//    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList) {
//        return productService.findList(productIdList);
//    }
//
//    @PostMapping("/decreaseStock")
//    public String decreaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList) {
//        productService.decreaseStock(descreaseStockInputList);
//        return "decreaseStock ok";
//    }
//    @PostMapping("/increaseStock")
//    public String increaseStock(@RequestBody List<DescreaseStockInput> descreaseStockInputList) {
//        productService.increaseStock(descreaseStockInputList);
//        return "increaseStock ok";
//    }
}
