package com.wddyxd.userservice.pojo;


import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
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
    private String name;
    private String permissionValue;
    private Boolean isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //层级
    @TableField(exist = false)
    private Integer level;

    //下级
    @TableField(exist = false)
    private List<Permissions> children;

    //是否选中
    @TableField(exist = false)
    private boolean isSelect;

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

    public String getPermissionValue() {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue) {
        this.permissionValue = permissionValue;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

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
}
