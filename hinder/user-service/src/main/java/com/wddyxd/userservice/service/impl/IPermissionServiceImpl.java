package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.PermissionsMapper;
import com.wddyxd.userservice.pojo.entity.Permission;
import com.wddyxd.userservice.service.Interface.IPermissionService;
import com.wddyxd.userservice.service.Interface.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 09:37
 **/

@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionsMapper, Permission> implements IPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionsService;


}
