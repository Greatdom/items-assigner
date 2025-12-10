package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.FlexibleCodeCheckerService;
import com.wddyxd.common.utils.RegexValidator;
import com.wddyxd.common.utils.encoder.PasswordEncoder;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdatePasswordDTO;
import com.wddyxd.userservice.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 21:31
 **/
@Component
public class PasswordUpdateStrategy implements UserUpdateStrategy<UpdatePasswordDTO>{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FlexibleCodeCheckerService flexibleCodeCheckerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void validate(UpdatePasswordDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(!Objects.equals(dto.getOldPassword(), userRelatedData.getUser().getPassword()))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(!RegexValidator.validatePhone(dto.getPhone()))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(flexibleCodeCheckerService.checkPhoneCodeWrong(dto.getPhone(), dto.getPhoneCode()))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdatePasswordDTO dto, UserRelatedData userRelatedData) {
        User user = userRelatedData.getUser();
        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodedPassword);
        userMapper.updateById(user);
    }

    @Override
    public Class<UpdatePasswordDTO> getDTOClass() {
        return UpdatePasswordDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER);
    }
}
