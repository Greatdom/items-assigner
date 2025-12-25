package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantLicenseDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 19:41
 **/

public interface IMerchantSupplementService extends IService<MerchantSupplement> {

    public String getShopName(Long id);

    public boolean getIsValidShop(Long id);

    public void add(MerchantSupplement merchantSupplement);

    public void delete(Long id);

    public void updateCustom(UpdateMerchantCustomDTO updateMerchantCustomDTO);

    public void updateLicense(UpdateMerchantLicenseDTO updateMerchantLicenseDTO);

    public void status(Long id);

}
