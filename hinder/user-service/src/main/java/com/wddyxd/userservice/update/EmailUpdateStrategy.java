package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.FlexibleCodeCheckerService;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateEmailDTO;
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
 * @create: 2025-12-09 22:01
 **/
@Component
public class EmailUpdateStrategy implements UserUpdateStrategy<UpdateEmailDTO>{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FlexibleCodeCheckerService flexibleCodeCheckerService;

    private static final Logger log = LoggerFactory.getLogger(EmailUpdateStrategy.class);

    @Override
    public void validate(UpdateEmailDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||dto==null) {
            log.error("用户被判空");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        if(!Objects.equals(dto.getOldEmail(), userRelatedData.getUser().getEmail())) {
            log.error("用户原邮箱错误");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        if(flexibleCodeCheckerService.checkEmailCodeWrong(dto.getNewEmail(), dto.getEmailCode())) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
    }

    @Override
    public void update(UpdateEmailDTO dto, UserRelatedData userRelatedData) {
        User user = userRelatedData.getUser();
        user.setEmail(dto.getNewEmail());
        userMapper.updateById(user);
    }

    @Override
    public Class<UpdateEmailDTO> getDTOClass() {
        return UpdateEmailDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER);
    }
}
