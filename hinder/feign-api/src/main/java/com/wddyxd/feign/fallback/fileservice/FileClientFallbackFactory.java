package com.wddyxd.feign.fallback.fileservice;


import com.wddyxd.common.constant.LogPrompt;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.fileservice.FileClient;
import com.wddyxd.feign.clients.userservice.AuthClient;
import com.wddyxd.feign.fallback.userservice.AuthClientFallbackFactory;
import com.wddyxd.feign.pojo.userservice.authcontroller.CurrentUserDTO;
import com.wddyxd.feign.pojo.userservice.authcontroller.PasswordSecurityGetterVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 13:06
 **/

@Component
public class FileClientFallbackFactory  implements FallbackFactory<FileClient> {

    static final Logger log = LoggerFactory.getLogger(FileClientFallbackFactory.class);

    @Override
    public FileClient create(Throwable cause) {
        return new FileClient() {
            @Override
            public Result<String> uploadCompressibleFile(MultipartFile file, Long userId) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

            @Override
            public Result<String> uploadIncompressibleFile(MultipartFile file, Long userId) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }

            @Override
            public Result<Void> deleteFile(Long userId, String fileName) {
                log.error(LogPrompt.FEIGN_ERROR.msg);
                return Result.error();
            }
        };
    }

}
