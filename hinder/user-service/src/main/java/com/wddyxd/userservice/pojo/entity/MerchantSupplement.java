package com.wddyxd.userservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 商户补强表,用于用户注册了商户的情况
 * @author: wddyxd
 * @create: 2025-11-23 23:08
 **/

@TableName("merchant_supplement")
public class MerchantSupplement implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private String shopName;
    private Long shopCategoryId;
    private String shopAddress;//店铺地址
    private String shopLicense;//营业执照
    private String shopLicenseImage;//营业执照图片
    private Integer shopStar;//评分
    private Integer shopStatus;//店铺状态
    private Boolean isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public String toString() {
        return "MerchantSupplement{" +
                "id=" + id +
                ", userId=" + userId +
                ", shopName='" + shopName + '\'' +
                ", shopCategoryId=" + shopCategoryId + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopLicense='" + shopLicense + '\'' +
                ", shopLicenseImage='" + shopLicenseImage + '\'' +
                ", shopStar=" + shopStar +
                ", shopStatus=" + shopStatus +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
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

    public Integer getShopStar() {
        return shopStar;
    }

    public void setShopStar(Integer shopStar) {
        this.shopStar = shopStar;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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
}
