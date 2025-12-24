package com.wddyxd.productservice.pojo.VO;


import com.wddyxd.productservice.pojo.entity.Coupon;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: 继承CouponVO
 * @author: wddyxd
 * @create: 2025-12-02 14:58
 **/

public class UserCouponVO extends Coupon {

    private Date getTime;//领取时间
    private Date useTime;//使用时间
    private Long orderId;//订单id
    private Integer status;//使用状态

    @Override
    public String toString() {
        return "UserCouponVO{" +
                super.toString() +
                "getTime=" + getTime +
                ", useTime=" + useTime +
                ", orderId=" + orderId +
                ", status=" + status +
                '}';
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

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }
}
