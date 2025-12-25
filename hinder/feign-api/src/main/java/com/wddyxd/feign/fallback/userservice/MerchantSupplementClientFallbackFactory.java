package com.wddyxd.feign.fallback.userservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.MerchantSupplementClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-16 15:57
 **/

@Component
public class MerchantSupplementClientFallbackFactory implements FallbackFactory<MerchantSupplementClient> {

    static final Logger log = LoggerFactory.getLogger(MerchantSupplementClientFallbackFactory.class);

    @Override
    public MerchantSupplementClient create(Throwable cause) {
        return new MerchantSupplementClient() {
            @Override
            public Result<String> getShopName(Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

            @Override
            public Result<Boolean> getIsValidShop(Long id) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }

}
