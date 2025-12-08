package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 19:37
 **/

public abstract class BaseUserUpdateDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
