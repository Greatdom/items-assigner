package com.wddyxd.userservice.pojo.VO;


/**
 * @program: items-assigner
 * @description: 访问某用户接口的响应体
 * @author: wddyxd
 * @create: 2025-12-01 15:15
 **/

public class UserVisitVO {

    private Long id;
    private String username;
    private String nickName;
    private String avatar;
    private Integer status;
    private Integer gender;
    private String birthday;
    private String region;
    private String shopName;
    private String shopCategory;
    private String shopAddress;
    private Integer shopStatus;

    @Override
    public String toString() {
        return "UserVisitVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status=" + status +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", region='" + region + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopCategory='" + shopCategory + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopStatus=" + shopStatus +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }
}
