package com.wddyxd.userservice.update;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 23:33
 **/
@Component
public class MerchantLicenseUpdateStrategy implements UserUpdateStrategy<UpdateMerchantLicenseDTO>{


    @Autowired
    private MerchantSupplementMapper merchantSupplementMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void validate(UpdateMerchantLicenseDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasMerchantSupplement()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateMerchantLicenseDTO dto, UserRelatedData userRelatedData) {
        MerchantSupplement merchantSupplement = userRelatedData.getMerchantSupplement();
        merchantSupplement.setShopLicense(dto.getShopLicense());
        merchantSupplement.setShopLicenseImage(dto.getShopLicenseImage());
        merchantSupplementMapper.updateById(merchantSupplement);
        userRoleMapper.insertUserRoleWithDeleteSameGroup(IdWorker.getId(), merchantSupplement.getUserId(), RoleConstant.ROLE_CUSTOM_MERCHANT.getId());

    }

    @Override
    public Class<UpdateMerchantLicenseDTO> getDTOClass() {
        return UpdateMerchantLicenseDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.MERCHANT_SUPPLEMENT);
    }
}
