package com.wddyxd.userservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.wddyxd.userservice.pojo.DTO.MerchantRegisterDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.pojo.entity.ShopCategory;
import com.wddyxd.userservice.service.Interface.*;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.*;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.RegexValidator;
import com.wddyxd.common.utils.encoder.EmailCodeGetter;
import com.wddyxd.common.utils.encoder.PasswordEncoder;
import com.wddyxd.common.utils.encoder.PhoneCodeGetter;
import com.wddyxd.security.security.UserInfoManager;
import com.wddyxd.userservice.mapper.AuthMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.CustomUserRegisterDTO;
import com.wddyxd.userservice.pojo.DTO.LoginUserForm;
import com.wddyxd.userservice.pojo.VO.EmailCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PasswordSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PhoneCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    private static final Logger log = LoggerFactory.getLogger(IAuthServiceImpl.class);

    @Autowired
    private IUserDetailService userDetailService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMerchantSupplementService merchantSupplementService;

    @Autowired
    private IShopCategoryService shopCategoryService;

    @Autowired
    private EmailCodeGetter emailCodeGetter;

    @Autowired
    private PhoneCodeGetter phoneCodeGetter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PasswordSecurityGetterVO passwordSecurityGetter(String username) {
        User user;
        if(RegexValidator.validateEmail(username)){
            user = baseMapper.selectOne(new QueryWrapper<User>().eq("email", username));
        }else if(RegexValidator.validatePhone(username)){
            user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", username));
        }else if(RegexValidator.validateUsername(username)){
            user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        }else{
            return null;
        }
        //判断能否找到用户
        if(user == null) {
            return null;
        }
        //获取用户信息并包装
        CurrentUserDTO currentUserDTO = currentUserDTOGetter(user.getId());
        if(currentUserDTO == null)return null;
        PasswordSecurityGetterVO passwordSecurityGetterVO = new PasswordSecurityGetterVO();
        passwordSecurityGetterVO.setCurrentUserDTO(currentUserDTO);
        passwordSecurityGetterVO.setPassword(user.getPassword());
        return passwordSecurityGetterVO;

    }

    @Override
    public PhoneCodeSecurityGetterVO phoneCodeSecurityGetter(String phone) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));
        if(user == null) {
            return null;
        }
        //从redis拉取并删除手机验证码
        String redisKey = RedisKeyConstant.USER_LOGIN_PHONE_CODE.key + phone;
        String phoneCode = null;
        try {
            phoneCode = (String) redisTemplate.opsForValue().getAndDelete(redisKey);
            if(phoneCode == null) {
                return null;
            }
        } catch (Exception e) {
            log.error(LogPrompt.REDIS_SERVER_ERROR.msg);
            return null;
        }
        //获取用户信息并包装
        CurrentUserDTO currentUserDTO = currentUserDTOGetter(user.getId());
        if(currentUserDTO == null)return null;
        PhoneCodeSecurityGetterVO phoneCodeSecurityGetterVO = new PhoneCodeSecurityGetterVO();
        phoneCodeSecurityGetterVO.setCurrentUserDTO(currentUserDTO);
        phoneCodeSecurityGetterVO.setPhoneCode(phoneCode);
        return phoneCodeSecurityGetterVO;
    }

    @Override
    public EmailCodeSecurityGetterVO emailCodeSecurityGetter(String email) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if(user == null) {
            return null;
        }
        //从redis拉取并删除邮箱验证码
        String redisKey = RedisKeyConstant.USER_LOGIN_EMAIL_CODE.key + email;
        String emailCode = (String) redisTemplate.opsForValue().getAndDelete(redisKey);
        if(emailCode == null) {
            return null;
        }
        //获取用户信息并包装
        CurrentUserDTO currentUserDTO = currentUserDTOGetter(user.getId());
        if(currentUserDTO == null)return null;
        EmailCodeSecurityGetterVO emailCodeSecurityGetterVO = new EmailCodeSecurityGetterVO();
        emailCodeSecurityGetterVO.setCurrentUserDTO(currentUserDTO);
        emailCodeSecurityGetterVO.setEmailCode(emailCode);
        return emailCodeSecurityGetterVO;
    }

    @Override
    public void phoneCode(String phone) {

        String redisKey = RedisKeyConstant.USER_LOGIN_PHONE_CODE.key + phone;

        //在redis取到计数器,如果取到了直接返回提示

        //判断手机号合法性
        if(!RegexValidator.validatePhone(phone))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
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
    @Transactional
    public void customUserRegister(CustomUserRegisterDTO customUserRegisterDTO) {
        if(customUserRegisterDTO == null)throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        checkCodeTrue(customUserRegisterDTO);
        if(this.checkUserUnique(customUserRegisterDTO))
            this.addUser(customUserRegisterDTO);
        else throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
    }

    @Override
    @Transactional
    public void merchantRegister(MerchantRegisterDTO merchantRegisterDTO) {
        if(merchantRegisterDTO == null)throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        ShopCategory shopCategory = shopCategoryService.getById(merchantRegisterDTO.getShopCategoryId());
        if(shopCategory == null) throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        CustomUserRegisterDTO customUserRegisterDTO = new CustomUserRegisterDTO();
        BeanUtil.copyProperties(merchantRegisterDTO, customUserRegisterDTO);
        checkCodeTrue(customUserRegisterDTO);
        if(this.checkUserUnique(customUserRegisterDTO)) {
            this.addUser(customUserRegisterDTO);
            this.addMerchant(merchantRegisterDTO);
        } else {
            this.addMerchant(merchantRegisterDTO);
        }
    }

    @Override
    public void rebuildPassword(CustomUserRegisterDTO customUserRegisterDTO) {

    }
    //所有字段都未匹配到用户 → true,所有匹配到的字段都指向同一个用户 → false,否则抛出异常
    private boolean checkUserUnique(CustomUserRegisterDTO customUserRegisterDTO) {
        //判断用户名,手机号和邮箱的合法性
        if(!RegexValidator.validateUsername(customUserRegisterDTO.getUsername())
                || !RegexValidator.validatePhone(customUserRegisterDTO.getPhone())
                || !RegexValidator.validateEmail(customUserRegisterDTO.getEmail())
        ) throw new CustomException(ResultCodeEnum.PARAM_ERROR);

        //一次查询所有匹配的未删除用户,匹配用户名/手机号/邮箱任一条件
        List<User> userList = baseMapper.selectList(new LambdaQueryWrapper<User>()
                .eq(User::getIsDeleted, 0)
                .and(wrapper -> wrapper
                        .eq(User::getUsername, customUserRegisterDTO.getUsername())
                        .or()
                        .eq(User::getPhone, customUserRegisterDTO.getPhone())
                        .or()
                        .eq(User::getEmail, customUserRegisterDTO.getEmail())));
        //分别记录用户名/手机号/邮箱匹配的用户ID
        Long usernameMatchId = null;
        Long phoneMatchId = null;
        Long emailMatchId = null;
        if (!CollectionUtils.isEmpty(userList)) {
            for (User user : userList) {
                if (customUserRegisterDTO.getUsername().equals(user.getUsername())) {
                    usernameMatchId = user.getId();
                }
                if (customUserRegisterDTO.getPhone().equals(user.getPhone())) {
                    phoneMatchId = user.getId();
                }
                if (customUserRegisterDTO.getEmail().equals(user.getEmail())) {
                    emailMatchId = user.getId();
                }
            }
        }
        //按规则判断返回值
        // 情况1：所有字段都未匹配到用户 → 返回0
        if (usernameMatchId == null && phoneMatchId == null && emailMatchId == null) {
            return true;
        }
        // 情况2：所有匹配到的字段都指向同一个用户 → 返回1
        Set<Long> matchIdSet = new HashSet<>();
        if (usernameMatchId != null) matchIdSet.add(usernameMatchId);
        if (phoneMatchId != null) matchIdSet.add(phoneMatchId);
        if (emailMatchId != null) matchIdSet.add(emailMatchId);
        if (matchIdSet.size() == 1) {
            return false;
        }
        throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
    }
    //如果手机和邮箱的验证码都正确则返回,否则抛出异常
    private void checkCodeTrue(CustomUserRegisterDTO customUserRegisterDTO){
        //获取手机验证码和邮箱验证码
        String redisPhoneCodeKey = RedisKeyConstant.USER_LOGIN_PHONE_CODE.key + customUserRegisterDTO.getPhone();
        String redisEmailCodeKey = RedisKeyConstant.USER_LOGIN_EMAIL_CODE.key + customUserRegisterDTO.getEmail();
        String phoneCode = (String) redisTemplate.opsForValue().getAndDelete(redisPhoneCodeKey);
        if(phoneCode == null || !phoneCode.equals(customUserRegisterDTO.getPhoneCode())
        )throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        String emailCode = (String) redisTemplate.opsForValue().getAndDelete(redisEmailCodeKey);
        if(emailCode == null || !emailCode.equals(customUserRegisterDTO.getEmailCode())
        )throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    //注册普通用户
    private void addUser(CustomUserRegisterDTO customUserRegisterDTO){


        User user = new User();
        user.setUsername(customUserRegisterDTO.getUsername());
        user.setPhone(customUserRegisterDTO.getPhone());
        user.setEmail(customUserRegisterDTO.getEmail());
        //加密密码
        user.setPassword(passwordEncoder.encode(customUserRegisterDTO.getPassword()));
        //注册并分配角色
        baseMapper.insert(user);
        UserDetail userDetail = new UserDetail();
        userDetail.setUserId(user.getId());
        userDetail.setMoney(new BigDecimal(0));
        userDetailService.add(userDetail);
        roleService.assign(user.getId(), new Long[]{RoleConstant.ROLE_NEW_USER.getId()});

    }

    private void addMerchant(MerchantRegisterDTO merchantRegisterDTO){
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, merchantRegisterDTO.getUsername())
                .eq(User::getPhone, merchantRegisterDTO.getPhone())
                .eq(User::getEmail, merchantRegisterDTO.getEmail()));
        if(user == null)
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        MerchantSupplement merchantSupplement = new MerchantSupplement();
        merchantSupplement.setUserId(user.getId());
        merchantSupplement.setName(merchantRegisterDTO.getShopName());
        merchantSupplement.setShopCategoryId(merchantRegisterDTO.getShopCategoryId());
        merchantSupplement.setShopAddress(merchantRegisterDTO.getShopAddress());
        merchantSupplement.setShopStatus(1);//关店
        merchantSupplementService.save(merchantSupplement);
        roleService.assign(user.getId(), new Long[]{RoleConstant.ROLE_NEW_MERCHANT.getId()});
    }

    private CurrentUserDTO currentUserDTOGetter(Long userId){
        //从redis拉取用户信息
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        String redisKey = RedisKeyConstant.USER_LOGIN_USERINFO.key + userId;
        try {
            Object object = redisTemplate.opsForValue().get(redisKey);
            if(object instanceof com.wddyxd.security.pojo.CurrentUserDTO tempCurrentUserDTO){
                BeanUtils.copyProperties(tempCurrentUserDTO,currentUserDTO);
                log.info(LogPrompt.REDIS_INFO.msg);
            }
        } catch (Exception e) {
            return null;
        }
        //否则用数据库获取用户信息
        if(currentUserDTO.getId()== null || currentUserDTO.getId() <= 0){
            log.info(LogPrompt.MYSQL_INFO.msg);
            currentUserDTO = baseMapper.getCurrentUserById(userId);
        }
        return currentUserDTO;
    }

}
