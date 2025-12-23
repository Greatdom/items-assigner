package com.wddyxd.fileservice.utils;


import com.wddyxd.common.constant.RedisKeyConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 15:27
 **/

@Component
public class FileRedisManager {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FilePathPackager filePathPackager;

    public void setFileMata(Long userId, String uniqueFileName){
        String key = RedisKeyConstant.FILE_META.key + userId + "_" + uniqueFileName;
        String filePath = filePathPackager.getTotalFileStoragePath(userId, uniqueFileName);
        try {
            redisTemplate.opsForValue().set(key, filePath,1, TimeUnit.HOURS);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }
    }

    public String getFileMeta(Long userId, String uniqueFileName){
        String key = RedisKeyConstant.FILE_META.key + userId + "_" + uniqueFileName;
        try {
            return (String) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }
    }

    public void deleteFileMeta(Long userId, String uniqueFileName){
        String key = RedisKeyConstant.FILE_META.key + userId + "_" + uniqueFileName;
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }
    }

}
