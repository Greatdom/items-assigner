package com.wddyxd.feign.clients.fallback;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.UserClient;
import com.wddyxd.feign.pojo.User;
import com.wddyxd.feign.pojo.securityPojo.SecurityUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-11 16:47
 **/

public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    static final Logger log = LoggerFactory.getLogger(UserClientFallbackFactory.class);

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public Result<User> FindById(Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

            @Override
            public Result<SecurityUserDTO> passwordSecurityGetter(String username) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }
}
