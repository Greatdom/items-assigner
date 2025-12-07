package com.wddyxd.productservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 商品规格实体类,与商品绑定
 * @author: wddyxd
 * @create: 2025-11-23 19:53
 **/
@TableName("product_sku")
public class ProductSku extends BaseEntity implements Serializable {
    private Long productId;
    private String specs;//'规格描述，如"红色-XL"',
    private BigDecimal price;//该规格的价格
    private Integer sales;//该规格的销量
    private Integer stock;//该规格的库存
    private String logo;

    @Override
    public String toString() {
        return "ProductSku{" +
                super.toString() +
                ", productId=" + productId +
                ", specs='" + specs + '\'' +
                ", price=" + price +
                ", sales=" + sales +
                ", stock=" + stock +
                ", logo='" + logo + '\'' +
                '}';
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
