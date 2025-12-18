package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-18 20:50
 **/

public class RoleAssignDTO {
    @Min(value = 1, message = "用户ID必须大于0", groups = { UpdateGroup.class })
    private Long userId;

    // 可选：如果用 List<Long> 更易校验，推荐
     @NotEmpty(message = "角色ID列表不能为空")
     @Size(max = 3, message = "一个用户最多只能分配3个角色")
     private List<@Min(value = 1, message = "角色ID必须大于0") Long> roleIds;

    @Override
    public String toString() {
        return "RoleAssignDTO{" +
                "userId=" + userId +
                ", roleIds=" + roleIds +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

}
