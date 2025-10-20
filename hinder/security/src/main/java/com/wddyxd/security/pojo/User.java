package com.wddyxd.security.pojo;


import java.io.Serializable;

/**
 * @program: items-assigner
 * @description: spring-security认证时用到的用户实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:23
 **/

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

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
}