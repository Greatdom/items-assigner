package com.wddyxd.feign.fallback;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.AuthClient;
import com.wddyxd.feign.pojo.userservice.authcontroller.EmailCodeSecurityGetterVO;
import com.wddyxd.feign.pojo.userservice.authcontroller.PasswordSecurityGetterVO;
import com.wddyxd.feign.pojo.userservice.authcontroller.PhoneCodeSecurityGetterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 17:04
 **/

public class AuthClientFallbackFactory implements FallbackFactory<AuthClient> {

    static final Logger log = LoggerFactory.getLogger(com.wddyxd.feign.fallback.AuthClientFallbackFactory.class);

    @Override
    public AuthClient create(Throwable cause) {
        return new AuthClient() {
            @Override
            public Result<PasswordSecurityGetterVO> passwordSecurityGetter(String username) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
            @Override
            public Result<PhoneCodeSecurityGetterVO> phoneCodeSecurityGetter(String phone) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
            @Override
            public Result<EmailCodeSecurityGetterVO> emailCodeSecurityGetter(String phone) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }
}
