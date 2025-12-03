package com.wddyxd.userservice.pojo.DTO;


import java.util.List;

/**
 * @program: items-assigner
 * @description: 包装用户信息
 * @author: wddyxd
 * @create: 2025-10-25 20:33
 **/

public class CurrentUserDTO {
    private Long id;
    private String username;
    private String nickName;
    private String avatar;
    private List<String> roles; //角色名称
    private List<String> permissionValueList; //权限值


    @Override
    public String toString() {
        return "CurrentUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", roles=" + roles +
                ", permissionValueList=" + permissionValueList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissionValueList() {
        return permissionValueList;
    }

    public void setPermissionValueList(List<String> permissionValueList) {
        this.permissionValueList = permissionValueList;
    }
}
