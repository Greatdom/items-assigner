package com.wddyxd.orderservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-23 23:53
 **/
@TableName("order_main")
public class OrderMain extends BaseEntity implements Serializable {
    private Long userId;
    private Long productId;
    private Long skuId;
    private Integer quantity;//购买数量
    private String productName;//商品名称快照
    private String skuSpecs;//规格快照
    private BigDecimal totalPrice;//订单总金额
    private BigDecimal payPrice;//实际支付金额
    private String remark;//订单备注
    private Integer status;//0-待付款 1-待发货 2-待收货 3-已完成 4-已取消
    private Integer payMethod;//0-未选择 1-微信支付 2-支付宝支付

    @Override
    public String toString() {
        return "OrderMain{" +
                super.toString() +
                ", userId=" + userId +
                ", productId=" + productId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", skuSpecs='" + skuSpecs + '\'' +
                ", totalPrice=" + totalPrice +
                ", payPrice=" + payPrice +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", payMethod=" + payMethod +
                '}';
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

}