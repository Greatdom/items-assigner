package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdatePasswordDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.service.Interface.IMerchantSupplementService;
import com.wddyxd.userservice.update.UserUpdateStrategy;
import com.wddyxd.userservice.update.UserUpdateStrategyFactory;
import com.wddyxd.userservice.update.UserUpdateTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 19:42
 **/
@Service
public class IMerchantSupplementServiceImpl extends ServiceImpl<MerchantSupplementMapper, MerchantSupplement> implements IMerchantSupplementService {


    @Autowired
    private UserUpdateStrategyFactory userUpdateStrategyFactory;

    @Autowired
    private UserUpdateTemplate updateTemplate;

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    private static final Logger log = LoggerFactory.getLogger(IMerchantSupplementServiceImpl.class);


    @Override
    public String getShopName(Long id) {
        MerchantSupplement merchantSupplement = baseMapper.selectOne(
                new LambdaQueryWrapper<>(MerchantSupplement.class)
                .eq(MerchantSupplement::getUserId, id));
        if(merchantSupplement == null||merchantSupplement.getIsDeleted()) {
            log.warn("商户信息不存在");
            return null;
        }
        System.out.println(merchantSupplement);
        return merchantSupplement.getName();
    }

    @Override
    public boolean getIsValidShop(Long id) {
        return baseMapper.getIsValidShop(id);
    }

    @Override
    @Transactional
    public void add(MerchantSupplement merchantSupplement) {
        baseMapper.insert(merchantSupplement);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void updateCustom(UpdateMerchantCustomDTO updateMerchantCustomDTO) {
        UserUpdateStrategy<UpdateMerchantCustomDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateMerchantCustomDTO.class);
        updateTemplate.update(updateMerchantCustomDTO, strategy);
    }

    @Override
    public void updateLicense(UpdateMerchantLicenseDTO updateMerchantLicenseDTO) {
        UserUpdateStrategy<UpdateMerchantLicenseDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateMerchantLicenseDTO.class);
        updateTemplate.update(updateMerchantLicenseDTO, strategy);
    }

    @Override
    public void status(Long id) {
        MerchantSupplement merchantSupplement = baseMapper.selectByUserId(id);
        if(merchantSupplement == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        Integer shopStatus = merchantSupplement.getShopStatus();
        merchantSupplement.setShopStatus(shopStatus == 1 ? 0 : 1);
        baseMapper.updateById(merchantSupplement);
    }
}
