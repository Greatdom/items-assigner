package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @program: items-assigner
 * @description: 更新性别接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:56
 **/

public class UpdateGenderDTO extends BaseUserUpdateDTO{

    @NotNull(message = "性别不能为空", groups = {UpdateGroup.class})
    @Min(value = 0, message = "性别不能小于0", groups = {UpdateGroup.class})
    @Max(value = 2, message = "性别不能大于2", groups = {UpdateGroup.class})
    private Integer gender;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}
