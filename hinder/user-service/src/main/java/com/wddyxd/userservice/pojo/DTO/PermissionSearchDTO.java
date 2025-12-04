package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.pojo.SearchDTO;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-04 19:24
 **/

public class PermissionSearchDTO extends SearchDTO {

    private RoleConstant role = RoleConstant.ROLE_SUPER_ADMIN;


    public RoleConstant getRole() {
        return role;
    }

    public void setRole(RoleConstant role) {
        this.role = role;
    }
}
