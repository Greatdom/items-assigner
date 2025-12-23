package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.fileservice.FileClient;
import com.wddyxd.feign.fallback.fileservice.FileClientFallbackFactory;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateAvatarDTO;
import com.wddyxd.userservice.pojo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 22:07
 **/

@Component
public class AvatarUpdateStrategy implements UserUpdateStrategy<UpdateAvatarDTO>{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileClient fileClient;

    static final Logger log = LoggerFactory.getLogger(AvatarUpdateStrategy.class);


    @Override
    public void validate(UpdateAvatarDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasUser()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateAvatarDTO dto, UserRelatedData userRelatedData) {
        //TODO 用分布式事务

        //远程调用上传文件接口来更新头像
        Result<String> getFileName = fileClient.uploadCompressibleFile(dto.getFile(), userRelatedData.getUser().getId());
        if(getFileName== null||getFileName.getCode()!=200||getFileName.getData()==null)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        //获取旧的user列数据
        User oldUser = userRelatedData.getUser();
        //更新成新的user数据
        User newUser = userRelatedData.getUser();
        newUser.setAvatar(getFileName.getData());
        userMapper.updateById(newUser);
        //TODO 可用异步 远程调用删除文件接口来删除旧头像
        if(oldUser.getAvatar()!=null&&!oldUser.getAvatar().isEmpty()){
            Result<Void> getDeleteResult = fileClient.deleteFile(oldUser.getId(), oldUser.getAvatar());
            if(getDeleteResult==null||getDeleteResult.getCode()!=200)
                log.warn("删除文件发生异常");
        }
    }

    @Override
    public Class<UpdateAvatarDTO> getDTOClass() {
        return UpdateAvatarDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.USER);
    }
}
