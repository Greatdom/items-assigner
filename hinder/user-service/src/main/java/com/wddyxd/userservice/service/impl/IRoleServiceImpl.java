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
import com.wddyxd.security.service.GetCurrentUserInfoService;
import com.wddyxd.userservice.mapper.RoleMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.RoleDTO;
import com.wddyxd.userservice.pojo.VO.RoleVO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserRole;
import com.wddyxd.userservice.service.Interface.IMerchantSupplementService;
import com.wddyxd.userservice.service.Interface.IRoleService;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import com.wddyxd.userservice.service.Interface.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    private static final Logger log = LoggerFactory.getLogger(IRoleServiceImpl.class);

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
    public void assign(Long userId, List<Long> roleIds) {
        //TODO幂等性问题
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

        //根据userId得到一个没有被删除的用户
        User user = userService.getById(userId);
        if (user == null || user.getIsDeleted()==true) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        //只有超级管理员才能分配管理员
        List<String> currentUserRoles = getCurrentUserInfoService.getCurrentUserRoles();
        if (!currentUserRoles.contains(RoleConstant.ROLE_SUPER_ADMIN.getName())&&groupRoleId[2]!=null) {
            log.error("当前用户没有权限分配管理员角色");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }

        //全局只能有一个超级管理员
        if(Objects.equals(groupRoleId[2], RoleConstant.ROLE_SUPER_ADMIN.getId())) {
            log.error("不能分配多余的超级管理员");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        log.info("开始分配角色...");
        userRoleService.assign(userId, roleIds);

        //如果用户之前没有被分配商户但现在被分配商户了就直接创建商户表
        if(groupRoleId[1]!=null){
            MerchantSupplement merchantSupplement = merchantSupplementService.getOne(
                    new LambdaQueryWrapper<>(MerchantSupplement.class)
                            .eq(MerchantSupplement::getUserId, userId)
            );

            if(merchantSupplement==null||merchantSupplement.getIsDeleted()){
                merchantSupplement = new MerchantSupplement();
                merchantSupplement.setUserId(userId);
                merchantSupplement.setShopStatus(1);//关店
                merchantSupplement.setShopCategoryId(ShopCategoryConstant.DEFAULT.getId());//默认分类
                merchantSupplement.setIsDeleted(false);
                merchantSupplementService.add(merchantSupplement);
            }
        }
    }

    @Override
    public void add(RoleDTO roleDTO) {

        //TODO幂等性问题
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setGroup(roleDTO.getGroup());
        this.save(role);
    }

    @Override
    public void update(RoleDTO roleDTO) {
        Role dbRole = this.getById(roleDTO.getId());
        if (dbRole == null || dbRole.getIsDeleted()) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        dbRole.setName(roleDTO.getName());
        dbRole.setGroup(roleDTO.getGroup());
        baseMapper.updateById(dbRole);
    }

    @Override
    public void delete(Long id) {

    }
}
