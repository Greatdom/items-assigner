package com.wddyxd.itemsservice.pojo.dto;


import com.wddyxd.feign.pojo.User;

import java.io.Serializable;

/**
 * @program: items-assigner
 * @description: test-order 数据传输类
 * @author: wddyxd
 * @create: 2025-10-20 22:11
 **/

public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long userId;
    private User user;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
