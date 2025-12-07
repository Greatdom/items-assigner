package com.wddyxd.productservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 商品分类实体类
 * @author: wddyxd
 * @create: 2025-11-23 19:47
 **/
@TableName("product_category")
public class ProductCategory extends BaseEntity implements Serializable {
    private String name;//分类名称

    @Override
    public String toString() {
        return "ProductCategory{" +
                super.toString() +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
