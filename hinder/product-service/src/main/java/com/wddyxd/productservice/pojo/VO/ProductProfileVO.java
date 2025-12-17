package com.wddyxd.productservice.pojo.VO;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 分页获取商品列表接口的响应体
 * @author: wddyxd
 * @create: 2025-12-01 20:35
 **/

public class ProductProfileVO {

    private Long id;

    private String productSkuName;

    private String productCategoryName;

    private Long userId;

    private String username;

    private String productName;

    private BigDecimal price;

    private Integer stock;

    private Integer sales;

    private Integer positiveComment;

    private Integer negativeComment;

    private String logo;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Boolean isDeleted;

    @Override
    public String toString() {
        return "ProductProfileVO{" +
                "id=" + id +
                ", productCategoryName='" + productCategoryName + '\'' +
                ", productSkuName='" + productSkuName + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", Stock=" + stock +
                ", sales=" + sales +
                ", positiveComment=" + positiveComment +
                ", negativeComment=" + negativeComment +
                ", logo='" + logo + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                '}';
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
