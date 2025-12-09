package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 19:43
 **/
@Mapper
public interface MerchantSupplementMapper extends BaseMapper<MerchantSupplement> {

    @Select("select * from merchant_supplement where user_id = #{userId}")
    public MerchantSupplement selectByUserId(Long userId);

}
