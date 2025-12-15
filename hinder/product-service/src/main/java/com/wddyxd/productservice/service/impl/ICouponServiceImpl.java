package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.productservice.mapper.CouponMapper;
import com.wddyxd.productservice.pojo.DTO.CouponDTO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
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
    public Page<Coupon> List(SearchDTO searchDTO) {
        return null;
    }

    @Override
    public List<Coupon> detail(Long id) {
        return null;
    }

    @Override
    public List<Coupon> visit(Long id) {
        return null;
    }

    @Override
    public void add(CouponDTO couponDTO) {
//        if(couponDTO==null)
//            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
//        if(couponDTO.getStock()<=0)
//            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
//        if(couponDTO.getTargetType()==1){
//            //根据targetId得到用户,如果不能得到或被删除则抛出异常
//        }else if(couponDTO.getTargetType()==2){
//            //得到商品
//        }else if(couponDTO.getTargetType()==null)
//            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
//        //当前时间是不是不晚于开始时间且开始时间要早于结束时间
//        //如果getType=0则value>0<100,否则value>0
//        //stock>0
//        //TODO validate
//        Coupon coupon = new Coupon();
//        BeanUtil.copyProperties(couponDTO, coupon);
//        baseMapper.insert(coupon);
    }

    @Override
    public void update(CouponDTO couponDTO) {

    }

    @Override
    public void delete(Long id) {

    }
}
