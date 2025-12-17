package com.wddyxd.productservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:47
 **/
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {


    List<Coupon> detailCouponVO(@Param("id") Long id);


    List<Coupon> visitCouponVO(@Param("id") Long id, @Param("userId") Long userId);

}
