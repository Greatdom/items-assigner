package com.wddyxd.feign.fallback.userservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.UserAddressClient;
import com.wddyxd.feign.clients.userservice.UserClient;
import com.wddyxd.feign.pojo.userservice.usercontroller.UpdateAvatarDTO;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserAddress;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-26 20:37
 **/
@Component
public class UserAddressClientFallbackFactory implements FallbackFactory<UserAddressClient> {

    static final Logger log = LoggerFactory.getLogger(UserAddressClientFallbackFactory.class);

    @Override
    public UserAddressClient create(Throwable cause) {
        return new UserAddressClient() {
            @Override
            public Result<UserAddress> getDefault(Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

            @Override
            public Result<UserAddress> getSpecial(Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }

}
