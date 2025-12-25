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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(IUserCouponServiceImpl.class);

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
        if(coupon== null||coupon.getIsDeleted()) {
            log.error("优惠券不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        Date now = new Date();
        //判断优惠券本身是否可用且在期限内
        if(coupon.getStatus()!=1
                ||coupon.getStartTime().after(now)
                ||coupon.getEndTime().before(now)) {
            log.error("优惠券不可用");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //判断优惠券库存是否已空
        if(coupon.getStock()-coupon.getSendingStock()<=0) {
            log.error("优惠券库存已空");
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
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
    public void consume(Long[] couponIds, Long orderId) {
        //TODO 也许优惠券不完全合法的时候可以进行消费但是要给警告
        for(Long couponId:couponIds)
            if(couponId==null || couponId<=0){
                log.error("优惠券id不能小于1");
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            }
        //TODO可优化条件的查询
        List<Coupon> coupons = couponService.list(new LambdaQueryWrapper<Coupon>()
                .in(Coupon::getId,couponIds)
                .eq(Coupon::getIsDeleted,false)
                .eq(Coupon::getStatus,1)
        );
        if(coupons.size()!=couponIds.length){
            log.error("优惠券列表不合法");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        Date now = new Date();
        List<UserCoupon> userCoupons = list(
                new LambdaQueryWrapper<UserCoupon>()
                        .eq(UserCoupon::getUserId, getCurrentUserInfoService.getCurrentUserId())
                        .in(UserCoupon::getCouponId,couponIds)
                        .eq(UserCoupon::getStatus,0)
        );
        if(userCoupons.size()!=coupons.size()){
            log.error("优惠券列表不合法");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        for(UserCoupon userCoupon:userCoupons) {
            userCoupon.setUseTime(now);
        userCoupon.setStatus(1);
        userCoupon.setOrderId(orderId);
        }
        updateBatchById(userCoupons);
    }

    @Override
    public void destroy(Long id) {

    }
}
