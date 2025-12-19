package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @program: items-assigner
 * @description: 更新基础商户信息接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 19:07
 **/

public class UpdateMerchantCustomDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "店铺名称不能为空", groups = {UpdateGroup.class})
    private String shopName;

    @NotNull(message = "店铺分类不能为空", groups = {UpdateGroup.class})
    @Min(message = "店铺分类不能小于1", value = 1, groups = {UpdateGroup.class})
    private Long shopCategoryId;

    @NotBlank(message = "店铺地址不能为空", groups = {UpdateGroup.class})
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
