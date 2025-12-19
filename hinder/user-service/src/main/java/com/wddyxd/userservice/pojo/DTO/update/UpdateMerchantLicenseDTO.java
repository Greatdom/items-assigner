package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 许可证认证接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 19:11
 **/

public class UpdateMerchantLicenseDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "许可证不能为空",groups = {UpdateGroup.class})
    private String ShopLicense;
    @NotBlank(message = "许可证图片不能为空",groups = {UpdateGroup.class})
    private String ShopLicenseImage;

    public String getShopLicense() {
        return ShopLicense;
    }

    public void setShopLicense(String shopLicense) {
        ShopLicense = shopLicense;
    }

    public String getShopLicenseImage() {
        return ShopLicenseImage;
    }

    public void setShopLicenseImage(String shopLicenseImage) {
        ShopLicenseImage = shopLicenseImage;
    }
}
