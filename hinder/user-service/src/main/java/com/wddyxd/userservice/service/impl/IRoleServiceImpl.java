package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.RoleMapper;
import com.wddyxd.userservice.pojo.VO.RoleVO;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserRole;
import com.wddyxd.userservice.service.Interface.IRoleService;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-09 17:17
 **/

@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public Page<Role> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), Role::getName, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public Result<RoleVO> detail(Long id) {
        //        查询role和permissions表中获取角色信息和角色关联的权限
//- 注意无论角色是否被逻辑删除都应该被被查到,但权限应该是没有被逻辑删除的
        return null;
    }

    @Override
    public Result<Void> assign(Long userId, Long[] roleIds) {
        return null;
    }

    @Override
    public Result<Void> add(String name) {
        return null;
    }

    @Override
    public Result<Void> update(String name) {
        return null;
    }

    @Override
    public Result<Void> delete(Long id) {
        return null;
    }
}
