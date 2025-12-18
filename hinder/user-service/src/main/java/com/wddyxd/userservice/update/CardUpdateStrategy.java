package com.wddyxd.userservice.update;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateCardDTO;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
