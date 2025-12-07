package com.wddyxd.userservice.pojo.DTO;


/**
 * @program: items-assigner
 * @description: 商户端商户注册接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 12:28
 **/

public class MerchantRegisterDTO extends CustomUserRegisterDTO{
    private String shopName;
    private String shopAddress;
    private Long shopCategoryId;

    @Override
    public String toString() {
        return "MerchantRegisterDTO{" +
                "shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopCategoryId=" + shopCategoryId +
                '}';
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(Long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

}
