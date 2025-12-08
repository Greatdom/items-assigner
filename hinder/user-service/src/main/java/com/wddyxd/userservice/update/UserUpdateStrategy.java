package com.wddyxd.userservice.update;


import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.BaseUserUpdateDTO;
import com.wddyxd.userservice.pojo.entity.User;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 19:58
 **/

public interface UserUpdateStrategy<T extends BaseUserUpdateDTO> {

    //差异化业务校验逻辑
    void validate(T dto, UserRelatedData userRelatedData);

    //字段更新
    void update(T dto, UserRelatedData userRelatedData);

    //获取当前策略处理的DTO类型(用于策略匹配)
    Class<T> getDTOClass();

    //获取当前策略需要加载的关联表
    List<RelatedTableType> needLoadTables();

    //关联表类型枚举
    enum RelatedTableType{
        USER,//用户表
        USER_DETAIL,//用户详情表
        MERCHANT_SUPPLEMENT,//商家补充表
        USER_ROLE//用户角色表
    }

}
