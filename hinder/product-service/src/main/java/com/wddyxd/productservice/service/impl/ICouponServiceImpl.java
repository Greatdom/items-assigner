package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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

        //得到商品id查询商品和商品所属的用户id

        //查询没有被删除的可用生效优惠券,其中
        //targetType==0则返回所有,targetType==1则关联用户id查询,targetType==2则关联商品id查询

        return null;
    }

    @Override
    public List<Coupon> visit(Long id) {
        //得到商品id查询商品和商品所属的用户id

        //查询没有被删除的可用生效优惠券,联合自己领取的可用的未使用的用户领取的优惠券,其中
        //targetType==0则返回所有,targetType==1则关联用户id查询,targetType==2则关联商品id查询

        return null;
    }

    @Override
    public void add(CouponDTO couponDTO) {
        //生成新优惠券
        Coupon coupon = new Coupon();
        BeanUtil.copyProperties(couponDTO, coupon);
        //设置pointer字段
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
    @Transactional
    public void update(CouponDTO couponDTO) {
        //查询旧优惠券
        Coupon coupon = baseMapper.selectById(couponDTO.getId());
        if(coupon==null||coupon.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        if(couponDTO.getStock()<=coupon.getSendingStock())
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        //判断是否需要修改pointer
        boolean isPointerUpdate = !Objects.equals(couponDTO.getTargetType(), coupon.getTargetType())
                || !Objects.equals(couponDTO.getTargetId(), coupon.getTargetId());
        //新的优惠券
        int couponStock = coupon.getStock();
        BeanUtil.copyProperties(couponDTO, coupon);
        //pointer设置
        if(isPointerUpdate){
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
        }
        // 执行乐观锁更新
        LambdaUpdateWrapper<Coupon> updateWrapper = Wrappers.lambdaUpdate(Coupon.class)
                .eq(Coupon::getId, coupon.getId())
                .eq(Coupon::getStock, couponStock)
                .eq(Coupon::getIsDeleted, false);

        int updateCount = baseMapper.update(coupon, updateWrapper);
        //TODO 可用异步通信技术添加重试机制
        if (updateCount == 0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
    }

    @Override
    public void delete(Long id) {

    }
}
