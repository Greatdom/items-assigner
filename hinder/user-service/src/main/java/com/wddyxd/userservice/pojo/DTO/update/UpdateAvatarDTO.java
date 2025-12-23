package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: items-assigner
 * @description: 更换头像接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 15:42
 **/

public class UpdateAvatarDTO extends BaseUserUpdateDTO{


    @NotNull(message = "file不能为空", groups = {UpdateGroup.class})
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
