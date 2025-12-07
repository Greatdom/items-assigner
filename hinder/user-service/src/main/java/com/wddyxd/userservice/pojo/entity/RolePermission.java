package com.wddyxd.userservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: role-permissions实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:56
 **/

@TableName("role_permission")
public class RolePermission extends BaseEntity implements Serializable {
    private Long roleId;
    private Long permissionId;

    @Override
    public String toString() {
        return "RolePermission{" +
                super.toString() +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

}
