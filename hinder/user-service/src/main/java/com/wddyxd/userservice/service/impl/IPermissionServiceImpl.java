package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.mapper.PermissionMapper;
import com.wddyxd.userservice.pojo.entity.Permission;
import com.wddyxd.userservice.pojo.entity.RolePermission;
import com.wddyxd.userservice.service.Interface.IPermissionService;
import com.wddyxd.userservice.service.Interface.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 09:37
 **/

@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private IRolePermissionService rolePermissionsService;

    @Override
    public Page<Permission> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<Permission> wrapper = Wrappers.lambdaQuery(Permission.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), Permission::getName, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    @Transactional
    public void assign(Long roleId, Long[] permissionIds) {
        //校验参数->过滤null -> 去重 -> 转回数组->再校验参数
        if(permissionIds == null|| roleId == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        int permissionIdLength = permissionIds.length;
        permissionIds = Arrays.stream(permissionIds)
                .filter(Objects::nonNull)
                .distinct()
                .toArray(Long[]::new);
        if(permissionIdLength!=permissionIds.length)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(permissionIdLength>0){
            //校验permissionIds指向的权限是否可以正常使用
            LambdaQueryWrapper<Permission> query = new LambdaQueryWrapper<>();
            query.in(Permission::getId, Arrays.asList(permissionIds));
            query.eq(Permission::getIsDeleted, 0);
            Long count = baseMapper.selectCount(query);
            if(count != permissionIds.length)
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        //执行旧权限删除


        //如果permissionIdLengthV2>0则执行新权限加入
        if(permissionIdLength>0){
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (Long permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissions.add(rolePermission);
            }
            //...
        }

    }

    @Override
    public void add(String name, String permissionValue) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setPermissionValue(permissionValue);
        this.save(permission);
    }

    @Override
    public void update(Long id, String name, String permissionValue) {
        Permission dbPermission = this.getById(id);
        if (dbPermission == null || dbPermission.getIsDeleted()) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        dbPermission.setName(name);
        dbPermission.setPermissionValue(permissionValue);
        baseMapper.updateById(dbPermission);
    }

    @Override
    public void delete(Long id) {
        Permission dbPermission = this.getById(id);
        if(dbPermission == null){
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        dbPermission.setIsDeleted(true);
        baseMapper.updateById(dbPermission);
    }
}
