package com.wddyxd.userservice.pojo.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.wddyxd.common.pojo.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: 用户详细信息
 * @author: wddyxd
 * @create: 2025-11-23 20:23
 **/

@TableName("user_detail")
public class UserDetail extends BaseEntity implements Serializable {
    private Long userId;
    private String realName;//真实姓名
    private Integer gender;// 性别
    private Date birthday;// 生日
    private String region;// 地区
    private String idCard;// 身份证号
    private Integer isIdCardVerified;// 身份证是否验证
    private BigDecimal money;// 账户余额


    @Override
    public String toString() {
        return "UserDetail{" +
                super.toString() +
                ", userId=" + userId +
                ", realName='" + realName + '\'' +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", region='" + region + '\'' +
                ", idCard='" + idCard + '\'' +
                ", isIdCardVerified=" + isIdCardVerified +
                ", money=" + money +
                '}';
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }



    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getIsIdCardVerified() {
        return isIdCardVerified;
    }

    public void setIsIdCardVerified(Integer isIdCardVerified) {
        this.isIdCardVerified = isIdCardVerified;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }


}


