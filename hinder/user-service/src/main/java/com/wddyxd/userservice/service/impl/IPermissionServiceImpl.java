package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.mapper.PermissionsMapper;
import com.wddyxd.userservice.pojo.entity.Permission;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.service.Interface.IPermissionService;
import com.wddyxd.userservice.service.Interface.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public Page<Permission> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<Permission> wrapper = Wrappers.lambdaQuery(Permission.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), Permission::getName, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public void assign(Long roleId, Long[] permissionIds) {
//        permissionIds都指向存在的正常运作的权限才可继续执行接口,
//- 根据参数查询角色和角色的权限,然后删除角色拥有的权限,然后重新增加权限
//- 只有超级管理员才可以给角色分配权限
//- 如果角色被删除则拒绝执行接口

        System.out.println(baseMapper.checkAllPermissionIdsValid(permissionIds));

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
        if (dbPermission == null || dbPermission.getDeleted()) {
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
        dbPermission.setDeleted(true);
        baseMapper.updateById(dbPermission);
    }
}
