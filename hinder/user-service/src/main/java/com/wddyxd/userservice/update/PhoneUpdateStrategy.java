package com.wddyxd.userservice.update;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.FlexibleCodeCheckerService;
import com.wddyxd.userservice.controller.UserController;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdatePhoneDTO;
import com.wddyxd.userservice.pojo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 21:53
 **/

@Component
public class PhoneUpdateStrategy implements UserUpdateStrategy<UpdatePhoneDTO>{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FlexibleCodeCheckerService flexibleCodeCheckerService;

    private static final Logger log = LoggerFactory.getLogger(PhoneUpdateStrategy.class);

    @Override
    public void validate(UpdatePhoneDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||dto==null) {
            log.error("用户被判空");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        if(!Objects.equals(dto.getOldPhone(), userRelatedData.getUser().getPhone())) {
            log.error("用户原手机号错误");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        if(flexibleCodeCheckerService.checkPhoneCodeWrong(dto.getNewPhone(), dto.getPhoneCode())) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
    }

    @Override
    public void update(UpdatePhoneDTO dto, UserRelatedData userRelatedData) {
        User user = userRelatedData.getUser();
        user.setPhone(dto.getNewPhone());
        userMapper.updateById(user);
    }

    @Override
    public Class<UpdatePhoneDTO> getDTOClass() {
        return UpdatePhoneDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER);
    }
}
