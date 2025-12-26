package com.wddyxd.feign.clients.userservice;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.userservice.UserClientFallbackFactory;
import com.wddyxd.feign.pojo.userservice.usercontroller.UpdateAvatarDTO;
import com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-13 16:35
 **/
// 正确写法：添加contextId，确保唯一
@FeignClient(
        value = "user-service",
        contextId = "userClient",
        fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient {

    @GetMapping("/user/user/profile/{id}")
    Result<UserProfileVO> profile(@PathVariable Long id);

    @GetMapping("/user/user/getUsername/{id}")
    Result<String> getUsername(@Min(value = 1, message = "ID必须大于0") @PathVariable Long id);

    @PutMapping("/user/user/update/avatar")
    @Operation(summary = "更新头像接口", description = "更新头像")
    public Result<Void> updateAvatar(@Validated(UpdateGroup.class) @RequestBody UpdateAvatarDTO updateAvatarDTO);

    @GetMapping("/user/user/profiles")
    public Result<List<HashMap<String,UserProfileVO>>> profiles(@RequestParam Long[] buyers, @RequestParam Long[] merchants);

}
