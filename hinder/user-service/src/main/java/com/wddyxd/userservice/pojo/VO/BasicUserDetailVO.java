package com.wddyxd.userservice.pojo.VO;


import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 11:26
 **/

public class BasicUserDetailVO extends UserProfileVO{
    private String phone;
    private String email;
    private String realName;
    private String gender;
    private Date birthday;
    private String region;
    private String idCard;
    private Integer isIdCardVerified;
    private BigDecimal money;
    private Integer status;
    private Boolean isDeleted;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "BasicUserDetailVO{" +
                super.toString() +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", realName='" + realName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", region='" + region + '\'' +
                ", idCard='" + idCard + '\'' +
                ", isIdCardVerified=" + isIdCardVerified +
                ", money=" + money +
                ", status=" + status +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
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
}
