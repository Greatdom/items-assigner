package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.mapper.PermissionsMapper;
import com.wddyxd.userservice.pojo.entity.Permission;
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

    }

    @Override
    public void add(String name, String permissionValue) {

    }

    @Override
    public void update(String name, String permissionValue) {

    }

    @Override
    public void delete(Long id) {

    }
}
