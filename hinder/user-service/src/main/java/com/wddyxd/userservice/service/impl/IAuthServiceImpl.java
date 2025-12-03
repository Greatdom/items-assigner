package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.AuthMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.CustomUserRegisterDTO;
import com.wddyxd.userservice.pojo.DTO.LoginUserForm;
import com.wddyxd.userservice.pojo.VO.EmailCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PasswordSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PhoneCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.service.Interface.IAuthService;
import com.wddyxd.userservice.service.Interface.IPermissionsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:05
 **/

@Service
public class IAuthServiceImpl extends ServiceImpl<AuthMapper, User> implements IAuthService {

    @Override
    public PasswordSecurityGetterVO passwordSecurityGetter(String username) {
        //TODO 可以现从redis拉取用户信息
        //TODO 判断是用户名,手机号还是邮箱
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if(user == null) {
            return null;
        }
        LoginUserForm loginUserForm = new LoginUserForm();
        BeanUtils.copyProperties(user,loginUserForm);
        CurrentUserDTO userInfo = baseMapper.getCurrentUserById(user.getId());
        if(userInfo == null) {
            return null;
        }
        PasswordSecurityGetterVO result = new PasswordSecurityGetterVO();
        result.setPassword(user.getPassword());
        result.setCurrentUserDTO(userInfo);
        return result;
    }

    @Override
    public PhoneCodeSecurityGetterVO phoneCodeSecurityGetter(String phone) {
        return null;
    }

    @Override
    public EmailCodeSecurityGetterVO emailCodeSecurityGetter(String email) {
        return null;
    }

    @Override
    public String phoneCode(String phone) {
        return "";
    }

    @Override
    public String emailCode(String email) {
        return "";
    }

    @Override
    public String customUserRegister(CustomUserRegisterDTO customUserRegisterDTO) {
        return "";
    }

    @Override
    public String merchantRegister(CustomUserRegisterDTO customUserRegisterDTO) {
        return "";
    }

    @Override
    public String rebuildPassword(CustomUserRegisterDTO customUserRegisterDTO) {
        return "";
    }

}
