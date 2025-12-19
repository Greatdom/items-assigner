package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 实名认证接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 16:07
 **/

public class UpdateCardDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "真实姓名不能为空", groups = {UpdateGroup.class})
    private String realName;

    @NotBlank(message = "身份证号不能为空", groups = {UpdateGroup.class})
    private String idCard;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
