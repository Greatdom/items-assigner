package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @program: items-assigner
 * @description: 商户端商户注册接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 12:28
 **/

public class MerchantRegisterDTO extends CustomUserRegisterDTO{
    @NotBlank(message = "商户名称不能为空", groups = {AddGroup.class})
    private String shopName;
    @NotBlank(message = "商户地址不能为空", groups = {AddGroup.class})
    private String shopAddress;
    @NotNull(message = "商户类别不能为空", groups = {AddGroup.class})
    @Min(value = 1, message = "商户类别不能小于1", groups = {AddGroup.class})
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
