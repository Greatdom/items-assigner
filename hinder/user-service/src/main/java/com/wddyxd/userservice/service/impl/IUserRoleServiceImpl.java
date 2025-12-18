package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.entity.RolePermission;
import com.wddyxd.userservice.pojo.entity.UserRole;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-10 20:25
 **/
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    @Transactional
    public void assign(Long userId, Long[] roleIds) {
        //已在rolesService作参数校验
        //删除该用户的所有关联角色
        LambdaUpdateWrapper<UserRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserRole::getUserId, userId)
                .set(UserRole::getIsDeleted, 1);
        baseMapper.update(null,updateWrapper);
        //用roleIds重新分配用户的角色
        if(roleIds.length>0){
            List<UserRole> userRoles = new ArrayList<>();
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setIsDeleted(false);
                userRoles.add(userRole);
            }
            this.saveBatch(userRoles);
        }
    }

    @Override
    @Transactional
    public void insertUserRoleWithDeleteSameGroup(Long userId, Long roleId) {
        if (userId == null || roleId == null) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }

        //查询目标角色的分组
        Integer targetGroup = baseMapper.selectRoleGroupByRoleId(roleId);

        //如果分组存在，先删除同用户同分组的旧角色关联
        if (targetGroup != null) {
            baseMapper.deleteSameGroupUserRole(userId, targetGroup);

            //插入新的用户-角色关联
            Long id = IdWorker.getId();
            UserRole userRole = new UserRole();
            userRole.setId(id);
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            baseMapper.insert(userRole);
        }
    }
}
