package com.wddyxd.fileservice.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.fileservice.mapper.UserFileBindMapper;
import com.wddyxd.fileservice.pojo.entity.UserFileBind;
import com.wddyxd.fileservice.service.Interface.IUserFileBindService;
import com.wddyxd.fileservice.utils.FilePathPackager;
import com.wddyxd.fileservice.utils.FileRedisManager;
import com.wddyxd.security.security.UserInfoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 15:20
 **/
@Service
public class IUserFileBindServiceImpl extends ServiceImpl<UserFileBindMapper, UserFileBind> implements IUserFileBindService {



    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FilePathPackager filePathPackager;

    @Autowired
    private FileRedisManager fileRedisManager;

    private static final Logger log = LoggerFactory.getLogger(IUserFileBindServiceImpl.class);

    @Override
    public String uploadCompressibleFile(MultipartFile file, Long userId) {
        validateFile(file);
        HashMap<String, String> map = transferToFile(userId, file, true);
        String filePath = map.get("filePath");
        String fileName = map.get("uniqueFileName");
        rabbitTemplate.convertAndSend(CommonConstant.FILE_COMPRESS_QUEUE,filePath);
        UserFileBind userFileBind = new UserFileBind();
        userFileBind.setFileName(fileName);
        userFileBind.setUserId(userId);
        userFileBind.setFileType(0);//压缩文件
        userFileBind.setIsValid(1);
        baseMapper.insert(userFileBind);
        fileRedisManager.setFileMata(userId,filePath);
        return fileName;
    }

    @Override
    public String uploadIncompressibleFile(MultipartFile file, Long userId) {
        validateFile(file);
        HashMap<String, String> map = transferToFile(userId, file, false);
        String filePath = map.get("filePath");
        String fileName = map.get("uniqueFileName");
        UserFileBind userFileBind = new UserFileBind();
        userFileBind.setFileName(fileName);
        userFileBind.setUserId(userId);
        userFileBind.setFileType(1);//不可压缩文件
        userFileBind.setIsValid(1);
        baseMapper.insert(userFileBind);
        fileRedisManager.setFileMata(userId,filePath);
        return fileName;
    }

    @Override
    public ResponseEntity<Resource> download(Long userId, String fileName) {
        try {
            // 从Redis获取文件路径
            String filePath = fileRedisManager.getFileMeta(userId, fileName);
            if(filePath == null){
                // 缓存未命中，查询MySQL
                UserFileBind userFileBind = baseMapper.selectOne(new LambdaQueryWrapper<UserFileBind>()
                        .eq(UserFileBind::getUserId, userId)
                        .eq(UserFileBind::getFileName, fileName));
                if(userFileBind == null||userFileBind.getIsDeleted()||userFileBind.getIsValid() != 1){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                filePath = filePathPackager.getTotalFileStoragePath(userFileBind);
                fileRedisManager.setFileMata(userId,filePath);
            }
            // 校验文件是否存在
            File file = new File(filePath);
            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // 封装文件资源
            Resource resource = new FileSystemResource(file);
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteFile(Long userId, String fileName) {
        // 1. 解除MySQL绑定关系
        int updateCount = baseMapper.update(null, new LambdaUpdateWrapper<UserFileBind>()
                .eq(UserFileBind::getUserId, userId)
                .eq(UserFileBind::getFileName, fileName)
                .set(UserFileBind::getIsValid, 0)
                .set(UserFileBind::getIsDeleted, true));
        if (updateCount == 0){
            log.error("删除文件失败");
            throw new CustomException(ResultCodeEnum.UNKNOWN_ERROR);
        }
        fileRedisManager.deleteFileMeta(userId,fileName);
        String filePath = filePathPackager.getTotalFileStoragePath(userId,fileName);
        rabbitTemplate.convertAndSend(CommonConstant.FILE_DELETE_QUEUE,filePath);
    }

    private void validateFile(MultipartFile file){
        if(file == null|| file.isEmpty())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(file.getSize() > CommonConstant.MAX_FILE_SIZE)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
    }

    private HashMap<String, String> transferToFile(Long userId, MultipartFile file, boolean isCompressible){
        // 生成唯一文件名(避免重复)
        String originalFileName = file.getOriginalFilename();
        String suffix = FileUtil.extName(originalFileName);
        String uniqueFileName = userId + "_" + IdUtil.simpleUUID() + "." + suffix;
        if(isCompressible && !isImageFile(suffix)){
            log.error("非容忍压缩文件不可压缩");
            throw new CustomException(ResultCodeEnum.UNKNOWN_ERROR);
        }
        // 创建用户存储目录
        String userDirPath = filePathPackager.getFileStorageDir(userId);
        File userDir = new File(userDirPath);
        if(!userDir.exists()){
            boolean mkDirs = userDir.mkdirs();
            if(!mkDirs){
                log.error("创建文件路径失败");
                throw new CustomException(ResultCodeEnum.UNKNOWN_ERROR);
            }
        }
        // 保存文件到本地
        String filePath = filePathPackager.getTotalFileStoragePath(userId, uniqueFileName);
        File destFile = new File(filePath);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            log.error("保存文件失败");
            throw new CustomException(ResultCodeEnum.UNKNOWN_ERROR);
        }
        HashMap<String, String> result = new HashMap<>();
        result.put("filePath",filePath);
        result.put("uniqueFileName",uniqueFileName);
        return result;
    }

    private boolean isImageFile(String suffix){
        for(String type : CommonConstant.IMAGE_TYPES){
            if(type.equalsIgnoreCase(suffix)){
                return true;
            }
        }
        return false;
    }

}
