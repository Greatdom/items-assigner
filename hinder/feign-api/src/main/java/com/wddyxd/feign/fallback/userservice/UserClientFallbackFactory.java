package com.wddyxd.feign.fallback.userservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.UserClient;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-13 16:36
 **/
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

    static final Logger log = LoggerFactory.getLogger(UserClientFallbackFactory.class);

    @Override
    public UserClient create(Throwable cause) {
        return new UserClient() {
            @Override
            public Result<UserProfileVO> profile(@PathVariable Long id){
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

            @Override
            public Result<String> getUsername(@PathVariable Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

        };
    }

}
