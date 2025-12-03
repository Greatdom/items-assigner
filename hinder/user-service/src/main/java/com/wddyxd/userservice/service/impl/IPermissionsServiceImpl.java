package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.PermissionsMapper;
import com.wddyxd.userservice.pojo.entity.Permission;
import com.wddyxd.userservice.pojo.entity.RolePermission;
import com.wddyxd.userservice.service.Interface.IPermissionsService;
import com.wddyxd.userservice.service.Interface.IRolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 09:37
 **/

@Service
public class IPermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permission> implements IPermissionsService {

    @Autowired
    private IRolePermissionsService rolePermissionsService;



    @Override
    public void assignPermissions(Long roleId, Long[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermission> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for(Long perId : permissionIds) {
            //RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            //封装到list集合
            rolePermissionList.add(rolePermission);
        }
        //添加到角色菜单关系表
        rolePermissionsService.saveBatch(rolePermissionList);
    }



    @Override
    public List<String> selectPermissionValueByUserId(Long id) {
        List<String> selectPermissionValueList = null;

        selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);

        return selectPermissionValueList;
    }
}
