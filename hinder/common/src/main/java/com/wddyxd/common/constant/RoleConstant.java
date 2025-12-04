package com.wddyxd.common.constant;

public enum RoleConstant {

    ROLE_SUPER_ADMIN(1984518164557385728L);

    private final Long id;

    RoleConstant(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
