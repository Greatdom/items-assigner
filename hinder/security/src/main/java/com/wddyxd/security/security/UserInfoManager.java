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
        try {
            if(securityUser == null|| securityUser.getCurrentUserInfo() == null||securityUser.getCurrentUserInfo().getId() == null){
                System.out.println("从Redis试图添加用户信息：用户信息为null");
                return;
            }
            CurrentUserInfo currentUserInfo = securityUser.getCurrentUserInfo();
            Long id = currentUserInfo.getId();
            String key = RedisKeyConstants.USER_LOGIN_USERINFO.key + id.toString();
            redisTemplate.opsForValue().set(key, currentUserInfo, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
            System.out.println("从Redis试图添加用户信息：添加成功");
        } catch (Exception e) {
            System.out.println("从Redis试图添加用户信息：Redis存储异常");
            throw new RuntimeException("用户信息缓存存储失败，请稍后重试",e);
        }
    }

    public CurrentUserInfo getInfoFromRedis(Long id) {
        String key = RedisKeyConstants.USER_LOGIN_USERINFO.key + id.toString();
        CurrentUserInfo currentUserInfo = null;
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if(value == null){
                System.out.println("从Redis查询用户信息：Key不存在，用户ID：" + id);
                return null;
            }
            if (!(value instanceof CurrentUserInfo)) {
                System.out.println("Redis中Key的类型不匹配，预期：CurrentUserInfo，实际不是");
                redisTemplate.delete(key);
                return null;
            }
            currentUserInfo = (CurrentUserInfo) value;
            redisTemplate.expire(key, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
            System.out.println("从Redis查询用户信息：查询成功，用户ID：" + id);
            return currentUserInfo;
        }catch (Exception e) {
            System.out.println("从Redis查询用户信息异常，用户ID：" + id);
            return null;
        }

    }
}
