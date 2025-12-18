package com.wddyxd.common.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum RoleConstant {

    ROLE_NEW_USER(1996956489134645252L,0,"ROLE_NEW_USER"),
    ROLE_CUSTOM_USER(1996956489134645251L,0,"ROLE_CUSTOM_USER"),
    ROLE_NEW_MERCHANT(1996956489134645250L,1,"ROLE_NEW_MERCHANT"),
    ROLE_CUSTOM_MERCHANT(1996956489134645249L,1,"ROLE_CUSTOM_MERCHANT"),
    ROLE_NEW_ADMIN(1996956489071730691L,2,"ROLE_NEW_ADMIN"),
    ROLE_CUSTOM_ADMIN(1996956489071730690L,2,"ROLE_CUSTOM_ADMIN"),
    ROLE_SUPER_ADMIN(1996956488421613570L,2,"ROLE_SUPER_ADMIN");



    private final Long id;
    private final Integer group;
    private final String name;
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

    RoleConstant(Long id,Integer group,String name){
        this.id = id;
        this.group = group;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Integer getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

}
