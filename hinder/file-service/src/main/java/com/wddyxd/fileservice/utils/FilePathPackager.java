package com.wddyxd.fileservice.utils;


import com.wddyxd.common.constant.CommonConstant;
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


    public String getTotalFileStoragePath(UserFileBind userFileBind){
        return CommonConstant.FILE_STORAGE_PATH + File.separator + userFileBind.getUserId() + File.separator + userFileBind.getFileName();
    }

    public String getTotalFileStoragePath(Long userId, String uniqueFileName){
        return CommonConstant.FILE_STORAGE_PATH + File.separator + userId + File.separator + uniqueFileName;
    }

    public String getFileStorageDir(Long userId){
        return CommonConstant.FILE_STORAGE_PATH + File.separator + userId;
    }

}
