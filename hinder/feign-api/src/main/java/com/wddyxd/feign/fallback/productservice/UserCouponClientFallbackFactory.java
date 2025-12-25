package com.wddyxd.feign.fallback.productservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.productservice.ProductSkuClient;
import com.wddyxd.feign.clients.productservice.UserCouponClient;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-26 01:24
 **/
@Component
public class UserCouponClientFallbackFactory implements FallbackFactory<UserCouponClient> {

    static final Logger log = LoggerFactory.getLogger(UserCouponClientFallbackFactory.class);

    @Override
    public UserCouponClient create(Throwable cause) {
        return new UserCouponClient() {

            @Override
            public Result<Void> consume(@NotNull(message = "优惠券id不能为空") Long[] couponIds, Long orderId) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }

}
