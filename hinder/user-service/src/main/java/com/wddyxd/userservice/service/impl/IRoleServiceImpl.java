package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.RoleMapper;
import com.wddyxd.userservice.pojo.Role;
import com.wddyxd.userservice.pojo.UserRole;
import com.wddyxd.userservice.service.IRoleService;
import com.wddyxd.userservice.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-09 17:17
 **/

@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        //查询所有的角色
        List<Role> allRolesList =baseMapper.selectList(null);

        //根据用户id，查询用户拥有的角色id
        List<UserRole> existUserRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));

        List<Long> existRoleList = existUserRoleList.stream().map(UserRole::getRoleId).toList();

        //对角色进行分类
        List<Role> assignRoles = new ArrayList<Role>();
        for (Role role : allRolesList) {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }
}
