package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import com.wddyxd.userservice.pojo.entity.UserRole;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 用户关联的多表数据载体
 * @author: wddyxd
 * @create: 2025-12-08 20:18
 **/

public class UserRelatedData {

    private User user;

    private UserDetail userDetail;

    private MerchantSupplement merchantSupplement;
    public boolean hasUser(){
        return user != null && !user.getIsDeleted();
    }

    public boolean hasUserDetail(){
        return userDetail != null && !userDetail.getIsDeleted();
    }

    public boolean hasMerchantSupplement(){
        return merchantSupplement != null && !merchantSupplement.getIsDeleted();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public MerchantSupplement getMerchantSupplement() {
        return merchantSupplement;
    }

    public void setMerchantSupplement(MerchantSupplement merchantSupplement) {
        this.merchantSupplement = merchantSupplement;
    }
}
