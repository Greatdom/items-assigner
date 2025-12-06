package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.constant.ShopCategoryConstant;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.RoleMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.VO.RoleVO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserRole;
import com.wddyxd.userservice.service.Interface.IMerchantSupplementService;
import com.wddyxd.userservice.service.Interface.IRoleService;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import com.wddyxd.userservice.service.Interface.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: 微服务脚手架
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-09 17:17
 **/

@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IMerchantSupplementService merchantSupplementService;

    @Override
    public Page<Role> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), Role::getName, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public RoleVO detail(Long id) {
        return baseMapper.detail(id);
    }

    @Override
    @Transactional
    public void assign(Long userId, Long[] roleIds) {
        //TODO幂等性问题
        //校验参数是否合法
        if (userId == null || roleIds == null) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        Long[] groupRoleId = new Long[CommonConstant.ROLE_GROUP_NUM];//0-用户 1-商户 2-管理员
        //O(n)时间复杂度,来确定最终的角色分组
            for (Long roleId : roleIds) {
                if(roleId==null){
                    throw new CustomException(ResultCodeEnum.PARAM_ERROR);
                }
                Integer group = RoleConstant.ROLE_ID_TO_GROUP_MAP.get(roleId);
                if (group == null||group>=CommonConstant.ROLE_GROUP_NUM||group<=-1) { // 仅处理枚举中存在的合法角色ID
                    //非法角色，直接拒绝分配
                    throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
                }
                //一个角色分组只能分配一个角色,否则拒绝分配
                if (groupRoleId[group] != null) {
                    throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR); // 新增：同一分组多角色错误码
                }
                //分组校验通过，记录该分组的角色ID
                groupRoleId[group] = roleId;
            }
        roleIds = Arrays.stream(roleIds)
                .filter(Objects::nonNull)  // 滤掉null值
                .toArray(Long[]::new);
        //根据userId得到一个没有被删除的用户
        User user = userService.getById(userId);
        if (user == null || user.getIsDeleted()==true) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        //只有超级管理员才能分配管理员

        //全局只能有一个超级管理员

        userRoleService.assign(userId, roleIds);

        //如果用户之前没有被分配商户但现在被分配商户了就直接创建商户表
//        MerchantSupplement merchantSupplement = new MerchantSupplement();
//        merchantSupplement.setUserId(userId);
//        merchantSupplement.setShopStatus(1);//关店
//        merchantSupplement.setShopCategoryId(ShopCategoryConstant.DEFAULT.getId());//默认分类
//        merchantSupplement.setIsDeleted(false);
//        merchantSupplementService.add(merchantSupplement);

        //(可选)如果用户没有许可证就不能给他分配非默认商户角色

        //(可选)如果用户没有实名认证就不能给他分配非默认用户角色
    }

    @Override
    public void add(String name,Integer group) {
        if(group==null||group>=CommonConstant.ROLE_GROUP_NUM||group<0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //TODO幂等性问题
        Role role = new Role();
        role.setName(name);
        role.setGroup(group);
        this.save(role);
    }

    @Override
    public void update(Role role) {
        if(role.getName()== null||role.getGroup()==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        Role dbRole = this.getById(role.getId());
        if (dbRole == null || dbRole.getIsDeleted()) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        dbRole.setName(role.getName());
        dbRole.setGroup(role.getGroup());
        baseMapper.updateById(dbRole);
    }

    @Override
    public void delete(Long id) {

    }
}
