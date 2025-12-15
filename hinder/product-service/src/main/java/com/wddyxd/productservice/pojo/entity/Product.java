package com.wddyxd.productservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 基础商品实体类
 * @author: wddyxd
 * @create: 2025-11-23 19:26
 **/

@TableName("product")
public class Product extends BaseEntity implements Serializable {
    private Long productSkuId;//商品skuId
    private String username;//商家名称
    private String name;//基础商品名
    private Integer sales;//总销量
    private Integer stock;//总库存
    private Long categoryId;//分类id
    private Integer positiveComment;
    private Integer negativeComment;
    private String description;//描述
    private Long userId;//用户id
    private Integer status;//状态

    @Override
    public String toString() {
        return "Product{" +
                super.toString() +
                ", username='" + username + '\'' +
                ", productSkuId=" + productSkuId +
                ", name='" + name + '\'' +
                ", sales=" + sales +
                ", stock=" + stock +
                ", categoryId=" + categoryId +
                ", positiveComment=" + positiveComment +
                ", negativeComment=" + negativeComment +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPositiveComment() {
        return positiveComment;
    }

    public void setPositiveComment(Integer positiveComment) {
        this.positiveComment = positiveComment;
    }

    public Integer getNegativeComment() {
        return negativeComment;
    }

    public void setNegativeComment(Integer negativeComment) {
        this.negativeComment = negativeComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}

