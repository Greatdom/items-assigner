package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.RedisKeyConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.productservice.mapper.CouponMapper;
import com.wddyxd.productservice.mapper.UserCouponMapper;
import com.wddyxd.productservice.pojo.VO.UserCouponVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.pojo.entity.UserCoupon;
import com.wddyxd.productservice.service.Interface.ICouponService;
import com.wddyxd.productservice.service.Interface.IUserCouponService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedissonClient redissonClient;

    private static final Logger log = LoggerFactory.getLogger(IUserCouponServiceImpl.class);

    @Override
    public List<UserCouponVO> List() {
        long userId = getCurrentUserInfoService.getCurrentUserId();
        return baseMapper.listUserCouponVO(userId);
    }

    @Override
    public void add(Long id) {
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
        Long user_id = getCurrentUserInfoService.getCurrentUserId();

        //生成用户的优惠券
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(user_id);
        userCoupon.setCouponId(id);
        userCoupon.setStatus(0);
        userCoupon.setGetTime(new Date());
        //执行抢券
        RLock lock = redissonClient.getLock(RedisKeyConstant.LOCK_COUPON.key+user_id);
        boolean isLock = false;
        int retryCount = 0;
        try{
            // 循环重试间隔1秒总耗时3秒
            while(retryCount<3){
                isLock = lock.tryLock(0,10, TimeUnit.SECONDS);
                if(isLock)break;
                retryCount++;
                log.warn("第{}次获取锁失败（用户ID：{}），1秒后重试", retryCount, user_id);
                Thread.sleep(1000);
            }
            // 所有重试完成后仍未获取锁
            if (!isLock) {
                log.error("用户ID：{} 3秒内重试3次仍未获取锁，抢券失败", user_id);
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
            }
            // 获取锁成功
            IUserCouponService proxy = (IUserCouponService) AopContext.currentProxy();
            proxy.createUserCoupon(coupon, userCoupon);
        }catch (InterruptedException e){
            // 处理线程中断异常,恢复中断状态
            log.error("抢券重试过程中线程被中断（用户ID：{}）", user_id, e);
            Thread.currentThread().interrupt();
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }finally {
            // 安全释放锁：仅当当前线程持有锁时才解锁
            if (isLock && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("用户ID：{} 抢券完成，已释放分布式锁", user_id);
            }
        }

    }

    @Transactional
    @Override
    public void createUserCoupon(Coupon coupon,UserCoupon userCoupon){
        Long id =coupon.getId();
        Long user_id = getCurrentUserInfoService.getCurrentUserId();
        //一人一券
        long count = new LambdaQueryChainWrapper<>(this.baseMapper)
                .eq(UserCoupon::getUserId, user_id)
                .eq(UserCoupon::getCouponId, id)
                .eq(UserCoupon::getIsDeleted, 0)
                .count();
        if(count>0){
            log.error("该用户已经抢过一次券了,user_id:{},coupon_id:{}",user_id,id);
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        //防止超拿,上乐观锁
        int updateCount = couponService.updateSendingStock(id, coupon.getVersion());
        if (updateCount == 0) {
            log.error("优惠券领取并发冲突，id:{}", id);
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        baseMapper.insert(userCoupon);
    }



    @Override
    @Transactional
    public List<Long> consume(Long[] couponIds, Long orderId) {
        //TODO 也许优惠券不完全合法的时候可以进行消费但是要给警告
        if(couponIds==null|| couponIds.length == 0)
            return null;
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

        //TODO返回实际使用的优惠券Id列表
        return null;
    }

    @Override
    public void destroy(Long id) {

    }
}
