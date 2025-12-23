package com.wddyxd.feign.clients.fileservice;


import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.fallback.fileservice.FileClientFallbackFactory;
import com.wddyxd.feign.fallback.userservice.AuthClientFallbackFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 13:06
 **/

@FeignClient(value = "file-service",
        contextId = "fileClient",
        fallbackFactory = FileClientFallbackFactory.class)
public interface FileClient {

    @PostMapping("/file/file/uploadCompressibleFile")
    public Result<String> uploadCompressibleFile(@NotNull(message = "file不能为空") @RequestParam MultipartFile file,
                                                 @Min(value = 1L, message = "userId不能小于1") @RequestParam Long userId);

    @PostMapping("/file/file/uploadIncompressibleFile")
    public Result<String> uploadIncompressibleFile(@NotNull(message = "file不能为空") @RequestParam MultipartFile file,
                                                   @Min(value = 1L, message = "userId不能小于1") @RequestParam Long userId);


    @DeleteMapping("/file/file/delete")
    @ResponseBody
    public Result<Void> deleteFile(@Min(value = 1L, message = "userId不能小于1") @RequestParam("userId") Long userId,
                                   @NotBlank(message = "fileName不能为空") @RequestParam("fileName") String fileName);

}
