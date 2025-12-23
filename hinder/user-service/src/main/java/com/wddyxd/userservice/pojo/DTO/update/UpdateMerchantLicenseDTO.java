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
    private String shopLicense;
    @NotBlank(message = "许可证图片不能为空",groups = {UpdateGroup.class})
    private String shopLicenseImage;

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
}
