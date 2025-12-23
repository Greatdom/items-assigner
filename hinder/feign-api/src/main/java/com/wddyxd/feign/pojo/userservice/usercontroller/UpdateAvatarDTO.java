package com.wddyxd.feign.pojo.userservice.usercontroller;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 14:04
 **/

public class UpdateAvatarDTO {

    private Long id;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
