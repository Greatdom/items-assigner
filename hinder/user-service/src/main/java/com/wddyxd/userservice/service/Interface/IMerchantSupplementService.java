package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 19:41
 **/

public interface IMerchantSupplementService extends IService<MerchantSupplement> {

    public void add(MerchantSupplement merchantSupplement);

    public void delete(Long id);

    public void updateCustom(UpdateMerchantCustomDTO updateMerchantCustomDTO);

    public void updateLicense(UpdateMerchantLicenseDTO updateMerchantLicenseDTO);

    public void status(Long id);

}
