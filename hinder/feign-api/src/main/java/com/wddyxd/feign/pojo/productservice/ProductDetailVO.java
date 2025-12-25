package com.wddyxd.feign.pojo.productservice;


import com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-25 21:53
 **/


public class ProductDetailVO {

    private ProductProfileVO productProfileVO;

    private UserProfileVO userProfileVO;

    private List<Coupon> coupon;

    private List<ProductSkuVO> productSkuVO;

    @Override
    public String toString() {
        return "ProductDetailVO{" +
                "productProfileVO=" + productProfileVO +
                ", userProfileVO=" + userProfileVO +
                ", coupon=" + coupon +
                ", productSkuVO=" + productSkuVO +
                '}';
    }

    public ProductProfileVO getProductProfileVO() {
        return productProfileVO;
    }

    public void setProductProfileVO(ProductProfileVO productProfileVO) {
        this.productProfileVO = productProfileVO;
    }

    public UserProfileVO getUserProfileVO() {
        return userProfileVO;
    }

    public void setUserProfileVO(UserProfileVO userProfileVO) {
        this.userProfileVO = userProfileVO;
    }

    public List<Coupon> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<Coupon> coupon) {
        this.coupon = coupon;
    }

    public List<ProductSkuVO> getProductSkuVO() {
        return productSkuVO;
    }

    public void setProductSkuVO(List<ProductSkuVO> productSkuVO) {
        this.productSkuVO = productSkuVO;
    }
}
