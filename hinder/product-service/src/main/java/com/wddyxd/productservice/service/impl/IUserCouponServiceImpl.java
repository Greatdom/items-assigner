package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.productservice.mapper.UserCouponMapper;
import com.wddyxd.productservice.pojo.VO.UserCouponVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.pojo.entity.UserCoupon;
import com.wddyxd.productservice.service.Interface.ICouponService;
import com.wddyxd.productservice.service.Interface.IUserCouponService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:53
 **/

@Service
public class IUserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon> implements IUserCouponService {

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    @Autowired
    private ICouponService couponService;

    @Override
    public List<UserCouponVO> List() {
        long userId = getCurrentUserInfoService.getCurrentUserId();
        return baseMapper.listUserCouponVO(userId);
    }

    @Override
    @Transactional
    public void add(Long id) {
        //TODO一人一券
        //得到旧优惠券
        Coupon coupon = couponService.getById(id);
        if(coupon== null||coupon.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        Date now = new Date();
        //判断优惠券本身是否可用且在期限内
        if(coupon.getStatus()!=1
                ||coupon.getStartTime().after(now)
                ||coupon.getEndTime().before(now))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //判断优惠券库存是否已空
        if(coupon.getStock()-coupon.getSendingStock()<=0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        //生成用户的优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(getCurrentUserInfoService.getCurrentUserId());
        userCoupon.setCouponId(id);
        userCoupon.setStatus(0);
        userCoupon.setGetTime(new Date());
        //TODO防止超拿,上乐观锁
        baseMapper.insert(userCoupon);
        coupon.setSendingStock(coupon.getSendingStock()+1);
        couponService.updateById(coupon);



    }

    @Override
    public void consume(Long id, Long orderId) {
//        用户在下单时进行优惠券的消费,传入orderId后生成useTime,status代表该优惠券被消费
        //得到待消费的优惠券
        Coupon coupon = couponService.getById(id);
        if(coupon== null||coupon.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        Date now = new Date();
        //判断优惠券本身是否可用且在期限内
        if(coupon.getStatus()!=1
                ||coupon.getStartTime().after(now)
                ||coupon.getEndTime().before(now))
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //得到用户领取的优惠券
        UserCoupon userCoupon = baseMapper.selectOne(
                new LambdaQueryWrapper<UserCoupon>()
                        .eq(UserCoupon::getCouponId, id)
                        .eq(UserCoupon::getUserId, getCurrentUserInfoService.getCurrentUserId())
        );
        if(userCoupon==null||userCoupon.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //判断优惠券是否被使用
        if(userCoupon.getStatus()!=0)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        userCoupon.setUseTime(now);
        userCoupon.setStatus(1);
        userCoupon.setOrderId(orderId);
        baseMapper.updateById(userCoupon);
    }

    @Override
    public void destroy(Long id) {

    }
}
