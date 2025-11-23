package com.wddyxd.productservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;

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
public class ProductSku  implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long productId;
    private String specs;//'规格描述，如"红色-XL"',
    private BigDecimal price;//该规格的价格
    private Integer stock;//该规格的库存
    private String skuCode;//商品规格编码
    private String logo;
    private Boolean isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public String toString() {
        return "ProductSku{" +
                "id=" + id +
                ", productId=" + productId +
                ", specs='" + specs + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", skuCode='" + skuCode + '\'' +
                ", logo='" + logo + '\'' +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
