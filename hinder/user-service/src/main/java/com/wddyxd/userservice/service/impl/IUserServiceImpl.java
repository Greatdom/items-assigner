package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.security.pojo.SecurityUser;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.dto.CurrentUserDTO;
import com.wddyxd.userservice.service.IPermissionsService;
import com.wddyxd.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    private UserMapper userMapper;

    @Autowired
    private IPermissionsService permissionsService;


    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }



//    @Override
//    public CurrentUserDTO me() {
////        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//
//        // 获取当前认证信息
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
//            // 强转为自定义的 UserDetails
//            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
//            Long userId = securityUser.getCurrentUserInfo().getId();// 获取 ID
//            System.out.println("当前用户 ID: " + userId);
//            String username = securityUser.getCurrentUserInfo().getUsername();
//            System.out.println("当前用户名: " + username);
//            return getUserInfo(username);
//        } else {
//            // 未认证或类型不匹配的处理
//            System.out.println("用户未登录或认证信息异常");
//            return null;
//        }
//    }

    @Override
    public CurrentUserDTO me() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null ) {
                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
                Long userId = securityUser.getCurrentUserInfo().getId();// 获取 ID
                System.out.println("当前用户 ID: " + userId);
                String username = securityUser.getCurrentUserInfo().getUsername();
                System.out.println("当前用户名: " + username);
                return getUserInfo(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取当前用户信息异常: " + e.getMessage());
        }
        return null;
    }

    @Override
    public CurrentUserDTO getUserInfo(String username) {
        // 校验用户名不为空
        if (username==null|| username.isEmpty()) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        // 1. 查询用户基本信息（过滤已删除用户）
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("username", username)
                .eq("is_deleted", 0); // 逻辑删除：0-未删除
        User user = userMapper.selectOne(userQuery);
        if (user == null)  throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);

        // 2. 查询用户关联的角色名称列表
        List<String> roles = userMapper.selectRoleNamesByUserId(user.getId());

        // 3. 查询用户关联的权限值列表
        List<String> permissionValues = permissionsService.selectPermissionValueByUserId(user.getId());

        // 4. 封装为CurrentUserDTO
        CurrentUserDTO dto = new CurrentUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setNickName(user.getNickName());
        dto.setSalt(user.getSalt());
        dto.setRoles(roles);
        dto.setPermissionValueList(permissionValues);

        return dto;
    }

    @Override
    public Result<?> selectAll(Integer pageNum,Integer pageSize,String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(search!=null&&!search.isEmpty()){
            wrapper.like(User::getNickName, search);
        }
        return Result.success(userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper));
    }



}
