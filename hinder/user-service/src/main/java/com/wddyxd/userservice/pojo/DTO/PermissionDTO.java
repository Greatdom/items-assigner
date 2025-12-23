package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-23 23:48
 **/

public class PermissionDTO {
    @Null(message = "ID必须为空",groups = {AddGroup.class})
    @NotNull(message = "ID不能为空",groups = {UpdateGroup.class})
    @Min(value = 1, message = "ID必须大于0",groups = {UpdateGroup.class})
    private Long id;
    @NotBlank(message = "权限名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    @NotBlank(message = "权限值不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String permissionValue;

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissionValue='" + permissionValue + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }
}
