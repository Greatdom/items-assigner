package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateAvatarDTO;
import com.wddyxd.userservice.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 22:07
 **/

@Component
public class AvatarUpdateStrategy implements UserUpdateStrategy<UpdateAvatarDTO>{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void validate(UpdateAvatarDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateAvatarDTO dto, UserRelatedData userRelatedData) {
        User user = userRelatedData.getUser();
        user.setAvatar(dto.getAvatar());
        userMapper.updateById(user);
    }

    @Override
    public Class<UpdateAvatarDTO> getDTOClass() {
        return UpdateAvatarDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER);
    }
}
