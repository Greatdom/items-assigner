package com.wddyxd.userservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: role 实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:53
 **/

@TableName("role")
public class Role extends BaseEntity implements Serializable {
    private String name;//角色名称
    private Integer group;

    @Override
    public String toString() {
        return "Role{" +
                super.toString() +
                ", name='" + name + '\'' +
                ", group=" + group +
                '}';
    }


    public String getName() {
        return name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }


}
