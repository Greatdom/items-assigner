package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateNickNameDTO;
import com.wddyxd.userservice.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 21:50
 **/

@Component
public class NickNameUpdateStrategy implements UserUpdateStrategy<UpdateNickNameDTO>{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void validate(UpdateNickNameDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||dto == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateNickNameDTO dto, UserRelatedData userRelatedData) {
        User user = userRelatedData.getUser();
        user.setNickName(dto.getNickName());
        userMapper.updateById(user);
    }

    @Override
    public Class<UpdateNickNameDTO> getDTOClass() {
        return UpdateNickNameDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER);
    }
}
