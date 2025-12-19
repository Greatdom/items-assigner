package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-19 20:07
 **/

public class RoleDTO {
    @Null(message = "ID必须为空",groups = {AddGroup.class})
    @NotNull(message = "ID不能为空",groups = {UpdateGroup.class})
    @Min(value = 1, message = "ID必须大于0",groups = {UpdateGroup.class})
    private Long id;
    @NotBlank(message = "角色名不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    @NotNull(message = "角色组不能为空",groups = {AddGroup.class, UpdateGroup.class})
    @Min(value = 0, message = "角色组必须大于等于0",groups = {AddGroup.class, UpdateGroup.class})
    @Max(value = 2, message = "角色组必须小于3",groups = {AddGroup.class, UpdateGroup.class})
    private Integer group;

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group=" + group +
                '}';
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

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
