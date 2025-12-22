package com.wddyxd.fileservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.fileservice.service.Interface.IUserFileBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-21 10:42
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IUserFileBindService userFileBindService;

    @PostMapping("/uploadCompressibleFile")
    public Result<String> uploadCompressibleFile(@RequestParam MultipartFile file,
                                              @RequestParam Long userId){
        return Result.success(userFileBindService.uploadCompressibleFile(file, userId));
    }

    @PostMapping("/uploadIncompressibleFile")
    public Result<String> uploadIncompressibleFile(@RequestParam MultipartFile file,
                                                   @RequestParam Long userId){
        return Result.success(userFileBindService.uploadIncompressibleFile(file, userId));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam Long userId,
                                             @RequestParam String fileName){
        return userFileBindService.download(userId, fileName);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Result<Void> deleteFile(@RequestParam("userId") String userId,
                                          @RequestParam("fileName") String fileName){
        userFileBindService.deleteFile(Long.parseLong(userId), fileName);
        return Result.success();
    }

}
