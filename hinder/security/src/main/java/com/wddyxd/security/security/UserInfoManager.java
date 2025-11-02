package com.wddyxd.security.security;


import com.wddyxd.common.constant.RedisKeyConstants;
import com.wddyxd.security.pojo.CurrentUserInfo;
import com.wddyxd.security.pojo.SecurityUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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
    private final Integer TOKEN_EXPIRE_DAYS = 7;

    // 注入 RedisTemplate
    public UserInfoManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveInfoInRedis(SecurityUser securityUser) {
        CurrentUserInfo currentUserInfo = securityUser.getCurrentUserInfo();
        Long id = currentUserInfo.getId();
        String key = RedisKeyConstants.USER_LOGIN_USERINFO + id.toString();
        redisTemplate.opsForValue().set(key, currentUserInfo, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
    }

    public CurrentUserInfo getInfoFromRedis(Long id) {
        String key = RedisKeyConstants.USER_LOGIN_USERINFO + id.toString();
        CurrentUserInfo currentUserInfo = (CurrentUserInfo) redisTemplate.opsForValue().get(key);
        redisTemplate.expire(key, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return currentUserInfo;
    }

}
