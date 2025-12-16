package com.wddyxd.productservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-23 23:21
 **/

@TableName("coupon")
public class Coupon extends BaseEntity implements Serializable {
    private String name;//优惠券名称
    private Integer getType;//0-满减 1-折扣
    private Integer targetType;//0-全场通用 1-指定商户可用 2-指定商品可用
    private Boolean isDiscount;//0-通用 1-商品优惠时不可用
    private Long targetId;//优惠券目标ID，无指定为0
    private BigDecimal threshold;//使用门槛
    private BigDecimal value;//优惠值（满减为金额，折扣为百分比）
    private Date startTime;//生效时间
    private Date endTime;//失效时间
    private Integer stock;//发放总量
    private Integer sendingStock;//领取数量
    private Integer status;//0-不可用 1-可用
    private Long version;//版本号
    private String pointer;//指向通用/商户名/商品名

    @Override
    public String toString() {
        return "Coupon{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", getType=" + getType +
                ", targetType=" + targetType +
                ", isDiscount=" + isDiscount +
                ", targetId=" + targetId +
                ", threshold=" + threshold +
                ", value=" + value +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", stock=" + stock +
                ", sendingStock=" + sendingStock +
                ", status=" + status +
                ", version=" + version +
                ", pointer='" + pointer + '\'' +
                '}';
    }
    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGetType() {
        return getType;
    }

    public void setGetType(Integer getType) {
        this.getType = getType;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Boolean getDiscount() {
        return isDiscount;
    }

    public void setDiscount(Boolean discount) {
        isDiscount = discount;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSendingStock() {
        return sendingStock;
    }

    public void setSendingStock(Integer sendingStock) {
        this.sendingStock = sendingStock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
