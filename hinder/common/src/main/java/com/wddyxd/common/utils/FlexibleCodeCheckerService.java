package com.wddyxd.common.utils;

import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.constant.RedisKeyConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 12:28
 **/
@Component
public class FlexibleCodeCheckerService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(FlexibleCodeCheckerService.class);


    // 加载灵活校验Lua脚本
    private final DefaultRedisScript<Object> flexibleCodeScript;

    private final String[] operationType = {"single", "double"};

    public FlexibleCodeCheckerService() {
        flexibleCodeScript = new DefaultRedisScript<>();
        String LUA_FILE_PATH = "FlexibleCodeChecker.lua";
        flexibleCodeScript.setLocation(new ClassPathResource(LUA_FILE_PATH));
        // 返回值类型设为Object（兼容Boolean/List）
        flexibleCodeScript.setResultType(Object.class);
    }

    // ====================== 单校验方法 ======================
    // 单校验：手机验证码
    public boolean checkPhoneCodeWrong(String phone, String phoneCode) {
        return singleCheck(RedisKeyConstant.USER_LOGIN_PHONE_CODE.key, phone, phoneCode);
    }

    // 单校验：邮箱验证码
    public boolean checkEmailCodeWrong(String email, String emailCode) {
        return singleCheck(RedisKeyConstant.USER_LOGIN_EMAIL_CODE.key, email, emailCode);
    }

    // 单校验通用逻辑
    private boolean singleCheck(String keyPrefix, String account, String inputCode) {
        // 1. 入参校验：空值直接判定为校验失败（原逻辑return true错误）
        if (account == null || inputCode == null || keyPrefix == null) {
            return true;
        }

        try {
            // 执行Lua脚本
            Object result = redisTemplate.execute(
                    flexibleCodeScript,
                    Collections.singletonList(keyPrefix),
                    account, inputCode, operationType[0]
            );

            // 2. 解析Lua返回值（兼容字符串/数字类型）
            String resultStr = result.toString();

            log.info("号码：{}，验证码：{}，验证结果：{}", account, inputCode, resultStr);

            // 3. 判定校验结果："1"=校验成功，其他=失败
            // 原逻辑return !checkSuccess 是反的，修正为直接返回checkSuccess
            return !"1".equals(resultStr);

        } catch (Exception e) {
            log.error(LogPrompt.LUA_ERROR.msg);
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }
    }
    // ====================== 双校验方法 ======================

    // 双校验：手机号+邮箱(全部成功才删Key),校验结果：[0]=手机校验结果，[1]=邮箱校验结果
    public boolean checkPhoneAndEmailCodeWrong(String phone, String phoneCode, String email, String emailCode) {
        if (phone == null || phoneCode == null || email == null || emailCode == null) {
            return true;
        }
        try {
            // 执行Lua脚本（双校验模式）
            Object result = redisTemplate.execute(
                    flexibleCodeScript,
                    Arrays.asList(RedisKeyConstant.USER_LOGIN_PHONE_CODE.key, RedisKeyConstant.USER_LOGIN_EMAIL_CODE.key),
                    phone, phoneCode, email, emailCode, "double" // 明确传double模式，避免operationType索引错误
            );
            log.info("手机号码：{}，手机验证码：{}，邮箱：{}，邮箱验证码：{}，验证结果：{}", phone, phoneCode, email, emailCode, result);
            // 解析返回值：双校验返回"1,1"/"1,0"等，单校验返回"1"/"0"
            if (result == null) {
                return true; // 无返回值判定失败
            }
            String resultStr = result.toString().trim();
            // 双校验场景：拆分结果
            if (resultStr.contains(",")) {
                String[] results = resultStr.split(",");
                // 必须拆分为2个，且都为"1"才校验通过（返回false）
                return !(results.length == 2 && "1".equals(results[0]) && "1".equals(results[1]));
            } else {
                // 非双校验返回格式，直接判定失败
                return true;
            }
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }
    }
}
