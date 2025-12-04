package com.wddyxd.common.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum RoleConstant {

    //TODO尚未分配角色
    ROLE_NEW_USER(-1L,0),
    ROLE_CUSTOM_USER(-2L,0),
    ROLE_NEW_MERCHANT(-3L,1),
    ROLE_CUSTOM_MERCHANT(-4L,1),
    ROLE_NEW_ADMIN(-5L,2),
    ROLE_CUSTOM_ADMIN(-6L,2),
    ROLE_SUPER_ADMIN(1984518164557385728L,2);



    private final Long id;
    private final Integer group;
    public static final Map<Long, Integer> ROLE_ID_TO_GROUP_MAP;

    static {
        // 初始化并包装为不可变Map，外部无法修改
        ROLE_ID_TO_GROUP_MAP = Collections.unmodifiableMap(Arrays.stream(RoleConstant.values())
                .collect(Collectors.toMap(
                        RoleConstant::getId,
                        RoleConstant::getGroup,
                        // 处理ID重复的情况（如果有），保留第一个
                        (existing, replacement) -> existing
                )));
    }

    RoleConstant(Long id,Integer group){
        this.id = id;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public Integer getGroup() {
        return group;
    }

}
