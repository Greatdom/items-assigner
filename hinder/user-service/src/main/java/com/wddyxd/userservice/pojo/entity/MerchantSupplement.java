package com.wddyxd.userservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 商户补强表,用于用户注册了商户的情况
 * @author: wddyxd
 * @create: 2025-11-23 23:08
 **/

@TableName("merchant_supplement")
public class MerchantSupplement extends BaseEntity implements Serializable {
    private Long userId;
    private String name;
    private Long shopCategoryId;
    private String shopAddress;//店铺地址
    private String shopLicense;//营业执照
    private String shopLicenseImage;//营业执照图片
    private Integer positiveComment;
    private Integer negativeComment;
    private Integer shopStatus;//店铺状态

    @Override
    public String toString() {
        return "MerchantSupplement{" +
                super.toString() +
                ", userId=" + userId +
                ", shopName='" + name + '\'' +
                ", shopCategoryId=" + shopCategoryId +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopLicense='" + shopLicense + '\'' +
                ", shopLicenseImage='" + shopLicenseImage + '\'' +
                ", positiveComment=" + positiveComment +
                ", negativeComment=" + negativeComment +
                ", shopStatus=" + shopStatus +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String shopName) {
        this.name = name;
    }

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLicense() {
        return shopLicense;
    }

    public void setShopLicense(String shopLicense) {
        this.shopLicense = shopLicense;
    }

    public String getShopLicenseImage() {
        return shopLicenseImage;
    }

    public void setShopLicenseImage(String shopLicenseImage) {
        this.shopLicenseImage = shopLicenseImage;
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

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

}
