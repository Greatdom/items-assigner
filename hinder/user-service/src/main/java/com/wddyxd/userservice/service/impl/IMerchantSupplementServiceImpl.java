package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.pojo.DTO.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.DTO.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.service.Interface.IMerchantSupplementService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 19:42
 **/
@Service
public class IMerchantSupplementServiceImpl extends ServiceImpl<MerchantSupplementMapper, MerchantSupplement> implements IMerchantSupplementService {

    @Override
    public void add(MerchantSupplement merchantSupplement) {
        baseMapper.insert(merchantSupplement);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void updateCustom(UpdateMerchantCustomDTO updateMerchantCustomDTO) {

    }

    @Override
    public void updateLicense(UpdateMerchantLicenseDTO updateMerchantLicenseDTO) {

    }

    @Override
    public void status(Long id) {

    }
}
