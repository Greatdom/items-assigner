package com.wddyxd.fileservice.pojo.entity;


import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 00:44
 **/

public class UserFileBind extends BaseEntity implements Serializable {

    private Long userId;
    private Integer fileType;//文件类型,0-允许压缩文件 1-不允许压缩文件
    private String fileName;//文件名
    // 是否有效 0-无效 1-有效
    private Integer isValid;

    @Override
    public String toString() {
        return "UserFileBind{" +
                "userId=" + userId +
                ", fileType=" + fileType +
                ", fileName='" + fileName + '\'' +
                ", isValid=" + isValid +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
