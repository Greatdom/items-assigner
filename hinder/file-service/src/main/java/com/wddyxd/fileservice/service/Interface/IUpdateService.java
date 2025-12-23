package com.wddyxd.fileservice.service.Interface;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 14:10
 **/

public interface IUpdateService {

    public String updateAvatar(MultipartFile file,
                               Long userId,
                               String oldAvatar);

}
