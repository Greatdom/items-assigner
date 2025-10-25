package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.UserRole;
import com.wddyxd.userservice.service.IUserRoleService;
import com.wddyxd.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:12
 **/
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public Result<User> login(User user) {
//        User dbUser = this.getOne(new QueryWrapper<User>().eq("name", user.getName()));
        User dbUser = query().eq("username", user.getUsername()).one();
        if(dbUser==null)
            throw new RuntimeException("用户不存在！");
        if(!user.getPassword().equals(dbUser.getPassword()))
            throw new RuntimeException("密码错误！");
        return Result.success(dbUser);
    }



    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    @Override
    public Result<User> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        List<String> permissionValueList =

//        Map<String, Object> result = new HashMap<>();
//        User user = userService.selectByUsername(username);
//        if (null == user) {
//            //throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
//        }
//
//        //根据用户id获取角色
//        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
//        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
//        if(roleNameList.size() == 0) {
//            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
//            roleNameList.add("");
//        }
//
//        //根据用户id获取操作权限值
//        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
//        redisTemplate.opsForValue().set(username, permissionValueList);
//
//        result.put("name", user.getUsername());
//        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//        result.put("roles", roleNameList);
//        result.put("permissionValueList", permissionValueList);
//        return result;
        return null;
    }
}
