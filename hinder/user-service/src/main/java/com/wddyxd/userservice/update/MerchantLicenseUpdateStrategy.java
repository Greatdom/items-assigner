package com.wddyxd.userservice.update;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.security.security.UserInfoManager;
import com.wddyxd.userservice.mapper.AuthMapper;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
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
    private IUserRoleService userRoleService;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private UserInfoManager userInfoManager;

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
        userRoleService.insertUserRoleWithDeleteSameGroup(merchantSupplement.getUserId(), RoleConstant.ROLE_CUSTOM_MERCHANT.getId());
        //在redis更新用户信息
        CurrentUserDTO currentUserDTO = authMapper.getCurrentUserById(merchantSupplement.getUserId());
        com.wddyxd.security.pojo.CurrentUserDTO securityCurrentUserDTO = new com.wddyxd.security.pojo.CurrentUserDTO();
        BeanUtil.copyProperties(currentUserDTO,securityCurrentUserDTO);
        userInfoManager.saveInfoInRedis(securityCurrentUserDTO);
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
