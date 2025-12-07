package com.wddyxd.userservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: user-role实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:55
 **/

@TableName("user_role")
public class UserRole extends BaseEntity implements Serializable {
    private Long userId;
    private Long roleId;

    @Override
    public String toString() {
        return "UserRole{" +
                super.toString() +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
