package com.wddyxd.userservice.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @program: items-assigner
 * @description: role-permissions实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:56
 **/

@TableName("role_permissions")
public class RolePermissions {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long roleId;
    private Long permissionsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Long permissionsId) {
        this.permissionsId = permissionsId;
    }
}
