package com.wddyxd.itemsservice.pojo;


import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @program: items-assigner
 * @description: test-order实体类
 * @author: wddyxd
 * @create: 2025-10-20 22:10
 **/


@TableName("orders")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long userId;

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


}
