package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdatePasswordDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.service.Interface.IMerchantSupplementService;
import com.wddyxd.userservice.update.UserUpdateStrategy;
import com.wddyxd.userservice.update.UserUpdateStrategyFactory;
import com.wddyxd.userservice.update.UserUpdateTemplate;
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

    }
}
