package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateGenderDTO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 20:34
 **/

@Component
public class GenderUpdateStrategy implements UserUpdateStrategy<UpdateGenderDTO> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public void validate(UpdateGenderDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||!userRelatedData.hasUserDetail())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(dto.getGender() == null||dto.getGender() < 0||dto.getGender() > 3)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateGenderDTO dto, UserRelatedData userRelatedData) {
        UserDetail userDetail = userRelatedData.getUserDetail();
        userDetail.setGender(dto.getGender());
        userDetailMapper.updateById(userDetail);
    }

    @Override
    public Class<UpdateGenderDTO> getDTOClass() {
        return UpdateGenderDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER, RelatedTableType.USER_DETAIL);
    }
}
