package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.MD5Encoder;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.UserRole;
import com.wddyxd.userservice.pojo.dto.CurrentUserDTO;
import com.wddyxd.userservice.pojo.securityDTO.SecurityUserDTO;
import com.wddyxd.userservice.service.IPermissionsService;
import com.wddyxd.userservice.service.IUserRoleService;
import com.wddyxd.userservice.service.IUserService;
import jakarta.annotation.PostConstruct;
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

    @Autowired
    private IPermissionsService permissionsService;

    IUserService proxy;

    @Override
    public void add(User user) {
        proxy = (IUserService) AopContext.currentProxy();

        proxy.addUserAndAssignRole(user, 1984518164557385730L);
    }

    @Override
    public void register(User user) {
        proxy = (IUserService) AopContext.currentProxy();

        proxy.addUserAndAssignRole(user, 1984521457576759298L);
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
        if (authentication != null && authentication.getPrincipal() instanceof com.wddyxd.security.pojo.CurrentUserInfo currentUserInfo) {
            Long id = currentUserInfo.getId();// 获取 ID
            // 校验用户ID不为空
            if (id == null) {
                throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
            }
            System.out.println("当前用户 ID: " + id);
            CurrentUserDTO userInfo = getUserInfo(id);
            if(userInfo == null){
                throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
            }else return userInfo;
        }else throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
    }

    @Override
    public SecurityUserDTO passwordSecurityGetter(String username) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user == null) {
            return null;
        }
        com.wddyxd.userservice.pojo.securityDTO.LoginUserForm loginUserForm = new com.wddyxd.userservice.pojo.securityDTO.LoginUserForm();
        BeanUtils.copyProperties(user,loginUserForm);
        CurrentUserDTO userInfo = getUserInfo(user.getId());
        if(userInfo == null)return null;
        com.wddyxd.userservice.pojo.securityDTO.CurrentUserInfo currentUserInfo = new com.wddyxd.userservice.pojo.securityDTO.CurrentUserInfo();
        BeanUtils.copyProperties(userInfo,currentUserInfo);
        return new SecurityUserDTO(loginUserForm,currentUserInfo);
    }


    @Override
    public CurrentUserDTO getUserInfo(Long id) {
        //TODO 可以现从redis拉取用户信息

        // 1. 查询用户基本信息（过滤已删除用户）
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("id", id)
                .eq("is_deleted", 0); // 逻辑删除：0-未删除
        User user = userMapper.selectOne(userQuery);
        if (user == null) return null;

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
            //TODO要给搜索的字段添加索引，而且不能只是按昵称查询
            wrapper.like(User::getNickName, search);
        }
        return Result.success(userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper));
    }



}
