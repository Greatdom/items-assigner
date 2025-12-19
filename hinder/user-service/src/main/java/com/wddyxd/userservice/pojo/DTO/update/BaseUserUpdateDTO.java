package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 19:37
 **/

public abstract class BaseUserUpdateDTO {
    @NotNull(message = "id不能为空", groups = {UpdateGroup.class})
    @Min(value = 1L, message = "id不能小于1", groups = {UpdateGroup.class})
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
