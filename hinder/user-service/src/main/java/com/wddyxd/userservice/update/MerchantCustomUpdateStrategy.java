package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.UpdateMerchantCustomDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 23:27
 **/

@Component
public class MerchantCustomUpdateStrategy implements UserUpdateStrategy<UpdateMerchantCustomDTO>{

    @Autowired
    private MerchantSupplementMapper merchantSupplementMapper;

    @Override
    public void validate(UpdateMerchantCustomDTO dto, UserRelatedData userRelatedData) {
        if(!userRelatedData.hasMerchantSupplement()||dto==null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
    }

    @Override
    public void update(UpdateMerchantCustomDTO dto, UserRelatedData userRelatedData) {
        MerchantSupplement merchantSupplement = userRelatedData.getMerchantSupplement();
        merchantSupplement.setName(dto.getShopName());
        merchantSupplement.setShopCategoryId(dto.getShopCategoryId());
        merchantSupplement.setShopAddress(dto.getShopAddress());
        merchantSupplementMapper.updateById(merchantSupplement);
    }

    @Override
    public Class<UpdateMerchantCustomDTO> getDTOClass() {
        return UpdateMerchantCustomDTO.class;
    }

    @Override
    public List<RelatedTableType> needLoadTables() {
        return List.of(RelatedTableType.MERCHANT_SUPPLEMENT);
    }
}
