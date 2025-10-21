package com.wddyxd.userservice.pojo;


import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * @program: items-assigner
 * @description: role-permissions实体类
 * @author: wddyxd
 * @create: 2025-10-20 20:56
 **/

@TableName("role_permissions")
public class RolePermissions {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long roleId;
    private Long permissionsId;
    private Boolean isDeleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
