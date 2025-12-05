package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.RolePermissionMapper;
import com.wddyxd.userservice.pojo.entity.RolePermission;
import com.wddyxd.userservice.service.Interface.IRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 20:27
 **/

@Service
public class IRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Override
    @Transactional
    public void assign(Long roleId, Long[] permissionIds) {
        //在permissionService里已完成了参数校验

        //执行旧权限删除
        LambdaUpdateWrapper<RolePermission> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RolePermission::getRoleId, roleId)
                .set(RolePermission::getIsDeleted, 1);
        baseMapper.update(null,updateWrapper);


        //如果permissionIdLengthV2>0则执行新权限加入
        if(permissionIds.length>0){
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermission.setIsDeleted(false);
                rolePermissions.add(rolePermission);
            }
            this.saveBatch(rolePermissions);
        }
    }
}
