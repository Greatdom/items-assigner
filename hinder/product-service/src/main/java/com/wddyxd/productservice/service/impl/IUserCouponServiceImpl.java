package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.productservice.mapper.UserCouponMapper;
import com.wddyxd.productservice.pojo.VO.UserCouponVO;
import com.wddyxd.productservice.pojo.entity.UserCoupon;
import com.wddyxd.productservice.service.Interface.IUserCouponService;
import org.springframework.stereotype.Service;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:53
 **/

@Service
public class IUserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon> implements IUserCouponService {

    @Override
    public UserCouponVO List() {
        return null;
    }

    @Override
    public void add(Long id) {

    }

    @Override
    public void consume(Long id, Long orderId) {

    }

    @Override
    public void destroy(Long id) {

    }
}
