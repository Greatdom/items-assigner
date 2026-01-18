package com.wddyxd.fileservice.service.impl;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.fileservice.FileClient;
import com.wddyxd.feign.clients.userservice.UserClient;
import com.wddyxd.feign.pojo.userservice.usercontroller.UpdateAvatarDTO;
import com.wddyxd.fileservice.service.Interface.IUpdateService;
import com.wddyxd.fileservice.service.Interface.IUserFileBindService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 14:10
 **/
@Component
public class IUpdateServiceImpl implements IUpdateService {

    @Autowired
    private IUserFileBindService userFileBindService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private FileClient fileClient;

    private static final Logger log = LoggerFactory.getLogger(IUpdateServiceImpl.class);

    @Override
    @GlobalTransactional
    public String updateAvatar(MultipartFile file, Long userId, String oldAvatar) {
        //调用上传文件接口来更新头像
        String avatar = userFileBindService.uploadIncompressibleFile(file, userId);
        //远程更新user数据
        UpdateAvatarDTO updateAvatarDTO = new UpdateAvatarDTO();
        updateAvatarDTO.setAvatar(avatar);
        updateAvatarDTO.setId(userId);
        Result<Void> getUpdateResult = userClient.updateAvatar(updateAvatarDTO);
        if(getUpdateResult ==null || getUpdateResult.getCode() != 200) {
            throw new CustomException(ResultCodeEnum.SERVER_ERROR);
        }
        //TODO 可异步调用删除接口
        if(oldAvatar != null&&!oldAvatar.isEmpty()){
            Result<Void> getDeleteResult = fileClient.deleteFile(userId, oldAvatar);
        }
        return avatar;
    }
}
