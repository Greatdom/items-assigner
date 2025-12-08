package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: 更新生日接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 16:02
 **/

public class UpdateBirthdayDTO extends BaseUserUpdateDTO{

    private String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
