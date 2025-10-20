package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.UserRole;
import com.wddyxd.userservice.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 20:25
 **/
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
