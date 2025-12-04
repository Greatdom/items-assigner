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

    private Long id;
    private String name;
    private Boolean isDeleted;
    private List<String> permissionNameList;
    private List<Permission> permissionValueList;

    @Override
    public String toString() {
        return "RoleVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isDeleted=" + isDeleted +
                ", permissionNameList=" + permissionNameList +
                ", permissionValueList=" + permissionValueList +
                '}';
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public List<String> getPermissionNameList() {
        return permissionNameList;
    }

    public void setPermissionNameList(List<String> permissionNameList) {
        this.permissionNameList = permissionNameList;
    }

    public List<Permission> getPermissionValueList() {
        return permissionValueList;
    }

    public void setPermissionValueList(List<Permission> permissionValueList) {
        this.permissionValueList = permissionValueList;
    }
}