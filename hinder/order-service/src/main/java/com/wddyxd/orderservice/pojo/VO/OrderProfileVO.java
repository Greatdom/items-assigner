package com.wddyxd.orderservice.pojo.VO;


import java.math.BigDecimal;

/**
 * @program: items-assigner
 * @description: 订单概要响应体
 * @author: wddyxd
 * @create: 2025-12-03 12:20
 **/

public class OrderProfileVO {

    private Long id;
    private Long userId;
    private Long productId;
    private Long skuId;
    private Integer quantity;

    private String productName;
    private String skuSpecs;
    private String logo;

    private BigDecimal totalPrice;
    private BigDecimal payPrice;

    private Integer status;
    private Boolean isDeleted;

    private UserProfileVO userProfileVO;

    @Override
    public String toString() {
        return "OrderProfileVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", skuSpecs='" + skuSpecs + '\'' +
                ", logo='" + logo + '\'' +
                ", totalPrice=" + totalPrice +
                ", payPrice=" + payPrice +
                ", status=" + status +
                ", isDeleted=" + isDeleted +
                ", userProfileVO=" + userProfileVO +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuSpecs() {
        return skuSpecs;
    }

    public void setSkuSpecs(String skuSpecs) {
        this.skuSpecs = skuSpecs;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public UserProfileVO getUserProfileVO() {
        return userProfileVO;
    }

    public void setUserProfileVO(UserProfileVO userProfileVO) {
        this.userProfileVO = userProfileVO;
    }
}
