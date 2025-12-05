package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.entity.UserRole;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 20:25
 **/

public interface IUserRoleService extends IService<UserRole> {

    void assign(Long userId, Long[] roleIds);

}
