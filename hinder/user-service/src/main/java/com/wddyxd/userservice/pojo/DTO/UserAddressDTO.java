package com.wddyxd.userservice.pojo.DTO;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

/**
 * @program: items-assigner
 * @description: 用户地址簿相关接口的请求体和响应体
 * @author: wddyxd
 * @create: 2025-12-01 17:06
 **/

public class UserAddressDTO {
    @Null(message = "ID必须为空",groups = {AddGroup.class})
    @NotNull(message = "ID不能为空",groups = {UpdateGroup.class})
    @Min(value = 1, message = "ID必须大于0",groups = {UpdateGroup.class})
    private Long id;
    @NotNull(message = "ID不能为空",groups = {AddGroup.class,UpdateGroup.class})
    @Min(value = 1, message = "ID必须大于0",groups = {AddGroup.class,UpdateGroup.class})
    private Long userId;
    @NotBlank(message = "收货人不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String receiver;
    @NotBlank(message = "手机号不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String phone;
    @NotBlank(message = "省不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String province;
    @NotBlank(message = "市不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String city;
    @NotBlank(message = "区不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String district;
    @NotBlank(message = "详细地址不能为空",groups = {AddGroup.class,UpdateGroup.class})
    private String address;
    @NotNull(message = "是否默认不能为空",groups = {AddGroup.class})
    private Boolean isDefault;

    @Override
    public String toString() {
        return "UserAddressVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
