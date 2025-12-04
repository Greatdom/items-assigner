package com.wddyxd.userservice.pojo.VO;


import com.wddyxd.userservice.pojo.entity.Permission;
import com.wddyxd.userservice.pojo.entity.Role;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 查看角色详细信息接口的响应体,携带Role和List<Permission>
 * @author: wddyxd
 * @create: 2025-12-01 19:39
 **/

public class RoleVO {

    private Role role;
    private List<Permission> permissions;

    @Override
    public String toString() {
        return "RoleVO{" +
                "role=" + role +
                ", permissions=" + permissions +
                '}';
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}