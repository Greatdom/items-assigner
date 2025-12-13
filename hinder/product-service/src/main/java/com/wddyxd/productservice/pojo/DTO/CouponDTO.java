package com.wddyxd.productservice.pojo.DTO;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 优惠券请求体
 * @author: wddyxd
 * @create: 2025-12-02 14:40
 **/

public class CouponDTO {
    private Long id;
    private String name;
    private Integer getType;
    private Integer targetType;
    private Integer isDiscount;
    private Integer targetId = 0;
    private BigDecimal threshold;
    private BigDecimal value;
    private Date startTime;
    private Date endTime;
    private Integer stock;

    @Override
    public String toString() {
        return "CouponDTO{" +
                "id=" + id +
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
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Integer isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
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
}
