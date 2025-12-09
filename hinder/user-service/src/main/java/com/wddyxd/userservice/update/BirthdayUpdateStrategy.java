package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateBirthdayDTO;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 22:10
 **/

@Component
public class BirthdayUpdateStrategy implements UserUpdateStrategy<UpdateBirthdayDTO>{

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public void validate(UpdateBirthdayDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||!userRelatedData.hasUserDetail()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateBirthdayDTO dto, UserRelatedData userRelatedData) {
        UserDetail userDetail = userRelatedData.getUserDetail();
        userDetail.setBirthday(dto.getBirthday());
        userDetailMapper.updateById(userDetail);
    }

    @Override
    public Class<UpdateBirthdayDTO> getDTOClass() {
        return UpdateBirthdayDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER, RelatedTableType.USER_DETAIL);
    }
}
