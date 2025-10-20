package com.wddyxd.userservice.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * @program: items-assigner
 * @description: permission实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:54
 **/

@TableName("permissions")
public class Permissions {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long pid;
    private String name;
    private String type;
    private String permissionValue;
    private String path;
    private String component;


    //    @ApiModelProperty(value = "层级")
    @TableField(exist = false)
    private Integer level;

    //    @ApiModelProperty(value = "下级")
    @TableField(exist = false)
    private List<Permissions> children;

    //    @ApiModelProperty(value = "是否选中")
    @TableField(exist = false)
    private boolean isSelect;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Permissions> getChildren() {
        return children;
    }

    public void setChildren(List<Permissions> children) {
        this.children = children;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
}
