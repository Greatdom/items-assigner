package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.MD5Encoder;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.AuthMapper;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.LoginUserForm;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserRole;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.securityDTO.SecurityUserDTO;
import com.wddyxd.userservice.service.Interface.IPermissionsService;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import com.wddyxd.userservice.service.Interface.IUserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private IUserRoleService userRoleService;

    IUserService proxy;

    @Override
    public Result<?> List(SearchDTO searchDTO) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(searchDTO.getSearch()!=null&&!searchDTO.getSearch().isEmpty()){
            //TODO要给搜索的字段添加索引，而且不能只是按昵称查询
            wrapper.like(User::getNickName, searchDTO.getSearch());
        }
        return Result.success(userMapper.selectPage(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()),wrapper));
    }



    @Override
    public void add(User user) {
        proxy = (IUserService) AopContext.currentProxy();

        proxy.addUserAndAssignRole(user, 1984518164557385730L);
    }

    @Transactional
    @Override
    public void addUserAndAssignRole(User user, Long roleId) {
        user.setPassword(MD5Encoder.encrypt(user.getPassword()));
        save(user);
        UserRole userRole = new UserRole();
        if(user.getId() == null)System.out.println("用户ID为空");
        userRole.setUserId(user.getId());
        userRole.setRoleId(roleId);
        userRoleService.save(userRole);

    }

    @Override
    public CurrentUserDTO me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof com.wddyxd.security.pojo.CurrentUserDTO currentUserDTO) {
            CurrentUserDTO get = new CurrentUserDTO();
            BeanUtils.copyProperties(currentUserDTO,get);
            return get;
        }else throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
    }





}
