package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 更新昵称接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:54
 **/

public class UpdateNickNameDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "昵称不能为空", groups = {UpdateGroup.class})
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
