package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.productservice.mapper.CouponMapper;
import com.wddyxd.productservice.pojo.DTO.CouponDTO;
import com.wddyxd.productservice.pojo.VO.CouponVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.service.Interface.ICouponService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:47
 **/
@Service
public class ICouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Override
    public Page<CouponVO> List(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public List<CouponVO> detail(Long id) {
        return List.of();
    }

    @Override
    public List<CouponVO> visit(Long id) {
        return List.of();
    }

    @Override
    public void add(Coupon coupon) {

    }

    @Override
    public void update(CouponDTO couponDTO) {

    }

    @Override
    public void delete(Long id) {

    }
}
