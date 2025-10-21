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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

}
