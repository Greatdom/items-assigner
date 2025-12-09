package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateRegionDTO;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 22:15
 **/

@Component
public class RegionUpdateStrategy implements UserUpdateStrategy<UpdateRegionDTO>{

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Override
    public void validate(UpdateRegionDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUserDetail()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateRegionDTO dto, UserRelatedData userRelatedData) {
        UserDetail userDetail = userRelatedData.getUserDetail();
        userDetail.setRegion(dto.getRegion());
        userDetailMapper.updateById(userDetail);
    }

    @Override
    public Class<UpdateRegionDTO> getDTOClass() {
        return UpdateRegionDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER_DETAIL);
    }
}
