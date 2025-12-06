package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.RedisKeyConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.RegexValidator;
import com.wddyxd.common.utils.encoder.EmailCodeGetter;
import com.wddyxd.common.utils.encoder.PhoneCodeGetter;
import com.wddyxd.userservice.mapper.AuthMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.CustomUserRegisterDTO;
import com.wddyxd.userservice.pojo.DTO.LoginUserForm;
import com.wddyxd.userservice.pojo.VO.EmailCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PasswordSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PhoneCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.service.Interface.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:05
 **/

@Service
public class IAuthServiceImpl extends ServiceImpl<AuthMapper, User> implements IAuthService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public PasswordSecurityGetterVO passwordSecurityGetter(String username) {
        //TODO 可以先从redis拉取用户信息
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
    public void phoneCode(String phone) {

        String redisKey = RedisKeyConstant.USER_LOGIN_PHONE_CODE.key + phone;

        //在redis取到计数器,如果取到了直接返回提示

        //判断手机号合法性
        if(!RegexValidator.validatePhone(phone))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        PhoneCodeGetter phoneCodeGetter = new PhoneCodeGetter();
        String phoneCode = phoneCodeGetter.encode(phone);

        //在redis中添加手机验证码,过期时间为15分钟
        try {
            redisTemplate.opsForValue().set(redisKey,phoneCode, CommonConstant.REDIS_USER_LOGIN_PHONE_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }

        //在redis添加计数器,过期时间为60秒

    }

    @Override
    public void emailCode(String email) {

        String redisKey = RedisKeyConstant.USER_LOGIN_EMAIL_CODE.key + email;

        //在redis取到计数器,如果取到了直接返回提示

        //判断邮箱合法性
        if(!RegexValidator.validateEmail(email))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        EmailCodeGetter emailCodeGetter = new EmailCodeGetter();
        String emailCode = emailCodeGetter.encode(email);

        //在redis中添加邮箱验证码,过期时间为15分钟
        try {
            redisTemplate.opsForValue().set(redisKey,emailCode, CommonConstant.REDIS_USER_LOGIN_EMAIL_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }

        //在redis添加计数器,过期时间为60秒

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
