package com.example.product.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product_category") // 匹配数据库名称
@Entity
@DynamicUpdate //动态更新
@Data  // 去掉 get set
public class ProductCategory {

    //    类目Id
    @Id  //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //自增类型
    private Integer categoryId;
    //    类目名字
    private String categoryName;
    //    类目编号
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory(){

    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
