package com.wddyxd.productservice.pojo.VO;


import com.wddyxd.productservice.pojo.entity.Coupon;

/**
 * @program: items-assigner
 * @description: 优惠券相关接口的响应体
 * @author: wddyxd
 * @create: 2025-12-02 14:37
 **/

public class CouponVO extends Coupon {

    private String pointer;

    @Override
    public String toString() {
        return "CouponVO{" +
                super.toString() +
                "pointer='" + pointer + '\'' +
                '}';
    }

    public String getPointer() {
        return pointer;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }
}
