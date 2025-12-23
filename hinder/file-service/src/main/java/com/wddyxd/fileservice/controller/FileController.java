package com.wddyxd.fileservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.fileservice.service.Interface.IUserFileBindService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-21 10:42
 **/
@RestController
@RequestMapping("/file/file")
@Validated
public class FileController {

    @Autowired
    private IUserFileBindService userFileBindService;

    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/uploadCompressibleFile")
    public Result<String> uploadCompressibleFile(@NotNull(message = "file不能为空") @RequestParam MultipartFile file,
                                              @Min(value = 1L, message = "userId不能小于1") @RequestParam Long userId){
        log.info("file.uploadCompressibleFile");
        return Result.success(userFileBindService.uploadCompressibleFile(file, userId));
    }

    @PostMapping("/uploadIncompressibleFile")
    public Result<String> uploadIncompressibleFile(@NotNull(message = "file不能为空") @RequestParam MultipartFile file,
                                                   @Min(value = 1L, message = "userId不能小于1") @RequestParam Long userId){
        log.info("file.uploadIncompressibleFile");
        return Result.success(userFileBindService.uploadIncompressibleFile(file, userId));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@Min(value = 1L, message = "userId不能小于1") @RequestParam Long userId,
                                             @NotBlank(message = "fileName不能为空") @RequestParam String fileName){
        log.info("file.download");
        return userFileBindService.download(userId, fileName);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Result<Void> deleteFile(@Min(value = 1L, message = "userId不能小于1") @RequestParam("userId") Long userId,
                                   @NotBlank(message = "fileName不能为空") @RequestParam("fileName") String fileName){
        log.info("file.delete");
        userFileBindService.deleteFile(userId, fileName);
        return Result.success();
    }

}
