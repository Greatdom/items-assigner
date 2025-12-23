package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 更换头像接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:42
 **/

public class UpdateAvatarDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "头像不能为空", groups = {UpdateGroup.class})
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
