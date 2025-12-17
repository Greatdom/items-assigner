package com.wddyxd.productservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-23 23:36
 **/
@TableName("user_coupon")
public class UserCoupon extends BaseEntity implements Serializable {
    private Long userId;
    private Long couponId;//优惠券id
    private Date getTime;//领取时间
    private Date useTime;//使用时间
    private Long orderId;//使用订单id
    private Integer status;//0-未使用 1-已使用

    @Override
    public String toString() {
        return "UserCoupon{" +
                super.toString() +
                ", userId=" + userId +
                ", couponId=" + couponId +
                ", getTime=" + getTime +
                ", useTime=" + useTime +
                ", orderId=" + orderId +
                ", status=" + status +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
