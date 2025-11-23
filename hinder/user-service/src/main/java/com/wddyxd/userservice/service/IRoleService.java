package com.wddyxd.userservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.entity.Role;

import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-09 17:15
 **/

public interface IRoleService extends IService<Role> {

    void assignRole(Long userId, Long[] roleIds);

    List<Role> getByUser(String userId);

}
