package com.wddyxd.fileservice.utils;


import com.wddyxd.fileservice.pojo.entity.UserFileBind;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 15:39
 **/
@Component
public class FilePathPackager {

    private static final String fileStoragePath="E:/FILE_STORAGE/";

    public String getTotalFileStoragePath(UserFileBind userFileBind){
        return fileStoragePath + File.separator + userFileBind.getUserId() + File.separator + userFileBind.getFileName();
    }

    public String getTotalFileStoragePath(Long userId, String uniqueFileName){
        return fileStoragePath + File.separator + userId + File.separator + uniqueFileName;
    }

    public String getFileStorageDir(Long userId){
        return fileStoragePath + File.separator + userId;
    }

}
