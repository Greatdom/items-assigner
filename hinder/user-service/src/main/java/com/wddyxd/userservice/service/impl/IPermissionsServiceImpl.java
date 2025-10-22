package com.wddyxd.userservice.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.helper.MenuHelper;
import com.wddyxd.userservice.helper.PermissionsHelper;
import com.wddyxd.userservice.mapper.PermissionsMapper;
import com.wddyxd.userservice.pojo.Permissions;
import com.wddyxd.userservice.pojo.RolePermissions;
import com.wddyxd.userservice.service.IPermissionsService;
import com.wddyxd.userservice.service.IRolePermissionsService;
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
public class IPermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {

    @Autowired
    private IRolePermissionsService rolePermissionsService;

    @Override
    public List<Permissions> queryAllMenus() {
        QueryWrapper<Permissions> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permissions> permissionList = baseMapper.selectList(wrapper);

        List<Permissions> result = PermissionsHelper.build(permissionList);

        return result;
    }

    @Override
    public List<JSONObject> SelectMenu() {
        List<Permissions> list = baseMapper.selectList(null);
        List<Permissions> permissionList = PermissionsHelper.build(list);
        List<JSONObject> result = MenuHelper.build(permissionList);
        return result;
    }

    @Override
    public void assignPermissions(Long roleId, Long[] permissionIds) {
        //roleId角色id
        //permissionId菜单id 数组形式
        //1 创建list集合，用于封装添加数据
        List<RolePermissions> rolePermissionList = new ArrayList<>();
        //遍历所有菜单数组
        for(Long perId : permissionIds) {
            //RolePermission对象
            RolePermissions rolePermission = new RolePermissions();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionsId(perId);
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
