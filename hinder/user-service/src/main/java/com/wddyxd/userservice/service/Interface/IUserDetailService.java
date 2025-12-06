package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.entity.UserDetail;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 22:20
 **/

public interface IUserDetailService extends IService<UserDetail> {

    public void add(UserDetail userDetail);

}
