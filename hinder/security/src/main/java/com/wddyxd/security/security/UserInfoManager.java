package com.wddyxd.security.security;


import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.constant.RedisKeyConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.security.exception.SecurityAuthException;
import com.wddyxd.security.pojo.CurrentUserDTO;
import com.wddyxd.security.pojo.SecurityUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-02 20:37
 **/

@Component
public class UserInfoManager {


    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserInfoManager.class);

    public UserInfoManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveInfoInRedis(SecurityUser securityUser) {
        if(securityUser == null
                || securityUser.getCurrentUserInfo() == null
                ||securityUser.getCurrentUserInfo().getId() == null){
            log.error(LogPrompt.PARAM_EMPTY_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
        CurrentUserDTO currentUserInfo = securityUser.getCurrentUserInfo();
        Long id = currentUserInfo.getId();
        String key = RedisKeyConstant.USER_LOGIN_USERINFO.key + id.toString();

        try {
            redisTemplate.opsForValue().set(key, currentUserInfo, CommonConstant.REDIS_USER_LOGIN_USERINFO_EXPIRE_DAYS, TimeUnit.DAYS);
            log.info(LogPrompt.SUCCESS_INFO.msg);
        } catch (Exception e) {
            log.error(LogPrompt.REDIS_SERVER_ERROR.msg);
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }

    public CurrentUserDTO getInfoFromRedis(Long id) {
        String key = RedisKeyConstant.USER_LOGIN_USERINFO.key + id.toString();
        CurrentUserDTO currentUserInfo = null;
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (!(value instanceof CurrentUserDTO)) {
                log.error(LogPrompt.REDIS_GET_DATA_ERROR.msg);
                redisTemplate.delete(key);
                throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
            }
            currentUserInfo = (CurrentUserDTO) value;
            redisTemplate.expire(key, CommonConstant.REDIS_USER_LOGIN_USERINFO_EXPIRE_DAYS, TimeUnit.DAYS);
            log.info(LogPrompt.SUCCESS_INFO.msg);
            return currentUserInfo;
        }catch (Exception e) {
            throw new SecurityAuthException(ResultCodeEnum.SERVER_ERROR);
        }
    }
}
