package com.wddyxd.fileservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.fileservice.pojo.entity.UserFileBind;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 15:19
 **/

public interface IUserFileBindService extends IService<UserFileBind> {

    public String uploadCompressibleFile(MultipartFile file, Long userId);

    public String uploadIncompressibleFile(MultipartFile file, Long userId);

    public ResponseEntity<Resource> download(Long userId, String fileName);

    public void deleteFile(Long userId, String fileName);


}
