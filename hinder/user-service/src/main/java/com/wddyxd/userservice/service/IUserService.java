package com.wddyxd.userservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.User;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:10
 **/

public interface IUserService extends IService<User> {
    Result<User> login(User user);

    void assignRole(Long userId, Long[] roleId);

    public User selectByUsername(String username);

}
