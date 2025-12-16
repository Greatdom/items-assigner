package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.MerchantSupplementClient;
import com.wddyxd.productservice.mapper.CouponMapper;
import com.wddyxd.productservice.pojo.DTO.CouponDTO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.service.Interface.ICouponService;
import com.wddyxd.productservice.service.Interface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:47
 **/
@Service
public class ICouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Autowired
    private IProductService productService;

    @Autowired
    private MerchantSupplementClient merchantSupplementClient;

    @Override
    public Page<Coupon> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<Coupon> wrapper = Wrappers.lambdaQuery(Coupon.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), Coupon::getName, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
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
        Coupon coupon = new Coupon();
        BeanUtil.copyProperties(couponDTO, coupon);
        //pointer设置
        if(couponDTO.getTargetType() == 1){
            Result<String> getShopName = merchantSupplementClient.getShopName(couponDTO.getTargetId());
            if(getShopName.getCode()!=200||getShopName.getData()==null)
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            coupon.setPointer(getShopName.getData());
        }else if(couponDTO.getTargetType() == 2){
            Product product = productService.getById(couponDTO.getTargetId());
            if(product==null||product.getIsDeleted())
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            coupon.setPointer(product.getName());
        }else if(couponDTO.getTargetType() == 0){
            coupon.setPointer("全场通用");
        }else{
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //sendingStock
        coupon.setSendingStock(0);
        //status=1,可用
        coupon.setStatus(1);
        baseMapper.insert(coupon);
    }

    @Override
    public void update(CouponDTO couponDTO) {
        Coupon coupon = baseMapper.selectById(couponDTO.getId());
        if(coupon==null||coupon.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(couponDTO.getStock()<=coupon.getSendingStock())
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        BeanUtil.copyProperties(couponDTO, coupon);
        //pointer设置
        if(couponDTO.getTargetType() == 1){
            Result<String> getShopName = merchantSupplementClient.getShopName(couponDTO.getTargetId());
            if(getShopName.getCode()!=200||getShopName.getData()==null)
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            coupon.setPointer(getShopName.getData());
        }else if(couponDTO.getTargetType() == 2){
            Product product = productService.getById(couponDTO.getTargetId());
            if(product==null||product.getIsDeleted())
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            coupon.setPointer(product.getName());
        }else if(couponDTO.getTargetType() == 0){
            coupon.setPointer("全场通用");
        }else{
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        //TODO加乐观锁
        baseMapper.updateById(coupon);
    }

    @Override
    public void delete(Long id) {

    }
}
