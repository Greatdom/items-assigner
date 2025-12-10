package com.wddyxd.userservice.pojo.VO;


import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;

import java.util.List;

/**
 * @program: items-assigner
 * @description: 包装BasicUserDetailVO,CurrentUserDTO,List<userAddressVO>,MerchantDetailVO
 * @author: wddyxd
 * @create: 2025-12-01 13:13
 **/

public class UserDetailVO {

    private BasicUserDetailVO basicUserDetailVO;

    private List<UserAddressDTO> userAddressDTOS;

    private CurrentUserDTO currentUserDTO;

    private MerchantSupplement merchantDetail;

    @Override
    public String toString() {
        return "UserDetailVO{" +
                "basicUserDetailVO=" + basicUserDetailVO +
                ", userAddressDTOS=" + userAddressDTOS +
                ", currentUserDTO=" + currentUserDTO +
                ", merchantDetail=" + merchantDetail +
                '}';
    }

    public BasicUserDetailVO getBasicUserDetailVO() {
        return basicUserDetailVO;
    }

    public void setBasicUserDetailVO(BasicUserDetailVO basicUserDetailVO) {
        this.basicUserDetailVO = basicUserDetailVO;
    }

    public List<UserAddressDTO> getUserAddressDTOS() {
        return userAddressDTOS;
    }

    public void setUserAddressDTOS(List<UserAddressDTO> userAddressDTOS) {
        this.userAddressDTOS = userAddressDTOS;
    }

    public CurrentUserDTO getCurrentUserDTO() {
        return currentUserDTO;
    }

    public void setCurrentUserDTO(CurrentUserDTO currentUserDTO) {
        this.currentUserDTO = currentUserDTO;
    }

    public MerchantSupplement getMerchantDetail() {
        return merchantDetail;
    }

    public void setMerchantDetail(MerchantSupplement merchantDetail) {
        this.merchantDetail = merchantDetail;
    }
}
