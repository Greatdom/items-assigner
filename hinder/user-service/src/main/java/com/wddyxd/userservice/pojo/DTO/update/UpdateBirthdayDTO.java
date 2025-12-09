package com.wddyxd.userservice.pojo.DTO.update;


import java.util.Date;

/**
 * @program: items-assigner
 * @description: 更新生日接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 16:02
 **/

public class UpdateBirthdayDTO extends BaseUserUpdateDTO{

    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
