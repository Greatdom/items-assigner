package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: 更新基础商户信息接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 19:07
 **/

public class UpdateMerchantCustomDTO extends BaseUserUpdateDTO{

    private String shopName;

    private Long shopCategoryId;

    private String shopAddress;

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

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }
}
