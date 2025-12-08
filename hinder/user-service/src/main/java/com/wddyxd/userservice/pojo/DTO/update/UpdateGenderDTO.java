package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: 更新性别接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:56
 **/

public class UpdateGenderDTO extends BaseUserUpdateDTO{

    private Integer gender;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}
