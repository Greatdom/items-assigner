package com.wddyxd.fileservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.fileservice.service.Interface.IUpdateService;
import com.wddyxd.fileservice.service.Interface.IUserFileBindService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 14:08
 **/
@RestController
@RequestMapping("/file/update")
@Validated
public class UpdateController {

    @Autowired
    private IUpdateService updateService;

    @PostMapping("/update/avatar")
    public Result<String> updateAvatar(@NotNull(message = "file不能为空") @RequestParam MultipartFile file,
                                       @Min(value = 1L, message = "userId不能小于1") @RequestParam Long userId,
                                       @NotNull(message = "oldAvatar不能为空") String oldAvatar){
        return Result.success(updateService.updateAvatar(file, userId, oldAvatar));
    }

}
