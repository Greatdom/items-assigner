package com.wddyxd.userservice.update;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.security.security.UserInfoManager;
import com.wddyxd.userservice.mapper.AuthMapper;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateCardDTO;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 22:22
 **/

@Component
public class CardUpdateStrategy implements UserUpdateStrategy<UpdateCardDTO>{

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private UserInfoManager userInfoManager;

    @Override
    public void validate(UpdateCardDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUserDetail()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateCardDTO dto, UserRelatedData userRelatedData) {
        UserDetail userDetail = userRelatedData.getUserDetail();
        userDetail.setRealName(dto.getRealName());
        userDetail.setIdCard(dto.getIdCard());
        userDetail.setIsIdCardVerified(1);
        userDetailMapper.updateById(userDetail);
        userRoleService.insertUserRoleWithDeleteSameGroup(userDetail.getUserId(), RoleConstant.ROLE_CUSTOM_USER.getId());
        //在redis更新用户信息
        CurrentUserDTO currentUserDTO = authMapper.getCurrentUserById(userDetail.getUserId());
        com.wddyxd.security.pojo.CurrentUserDTO securityCurrentUserDTO = new com.wddyxd.security.pojo.CurrentUserDTO();
        BeanUtil.copyProperties(currentUserDTO,securityCurrentUserDTO);
        userInfoManager.saveInfoInRedis(securityCurrentUserDTO);
    }

    @Override
    public Class<UpdateCardDTO> getDTOClass() {
        return UpdateCardDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER_DETAIL);
    }
}
