package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.feign.clients.userservice.UserClient;
import com.wddyxd.productservice.controller.ProductController;
import com.wddyxd.productservice.mapper.ProductCategoryMapper;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.pojo.DTO.*;
import com.wddyxd.productservice.pojo.VO.ProductDetailVO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.pojo.VO.UserProfileVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.pojo.entity.ProductCategory;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import com.wddyxd.productservice.service.Interface.ICouponService;
import com.wddyxd.productservice.service.Interface.IProductCategoryService;
import com.wddyxd.productservice.service.Interface.IProductService;
import com.wddyxd.productservice.service.Interface.IProductSkuService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 12:05
 **/
@Service
public class IProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    @Autowired
    private IProductSkuService productSkuService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ICouponService couponService;

    private static final Logger log = LoggerFactory.getLogger(IProductServiceImpl.class);


    @Override
    public Page<ProductProfileVO> List(ProductListDTO productListDTO) {
        Page<ProductProfileVO> page = new Page<>(productListDTO.getPageNum(), productListDTO.getPageSize());
        return baseMapper.getPageProductProfileVOManager(page, productListDTO.getSearch());
    }

    @Override
    public Page<ProductProfileVO> feed(ProductFeedDTO productFeedDTO) {
        Page<ProductProfileVO> page = new Page<>(productFeedDTO.getPageNum(), productFeedDTO.getPageSize());
        return baseMapper.getPageProductProfileVOFeed(page, productFeedDTO.getCategoryId(), productFeedDTO.getSortColumn().getColumn(), productFeedDTO.getSortOrder().getOrder());
    }

    @Override
    public ProductDetailVO visit(Long id) {
        ProductProfileVO productProfileVO = baseMapper.getProductProfileVOById(id);
        if(productProfileVO==null||productProfileVO.getIsDeleted()||productProfileVO.getStatus()!=1) {
            log.error("商品不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        Result<com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO> getUserProfileVO
                = userClient.profile(productProfileVO.getUserId());
        if(getUserProfileVO.getCode()!=200||getUserProfileVO.getData()==null)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        UserProfileVO userProfileVO = new UserProfileVO();
        BeanUtil.copyProperties(getUserProfileVO.getData(),userProfileVO);
        List<Coupon> coupons =  couponService.visit(id);
        List<ProductSkuVO> productSkus = productSkuService.List(id);
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setProductProfileVO(productProfileVO);
        productDetailVO.setCoupon(coupons);
        productDetailVO.setProductSkuVO(productSkus);
        productDetailVO.setUserProfileVO(userProfileVO);
        return productDetailVO;

        //       返回ProductDetailVO,这个类展示了商品详情页面的信息ProductProfileVO,
//- 用户概要指向商户UserProfileVO,优惠券指向用户领取的生效的可用优惠券CouponVO,商品规格是该商品的所有规格ProductSkuVO,
//- 用户端不应该访问被下架或删除的商品
    }

    @Override
    public ProductDetailVO detail(Long id) {
        ProductProfileVO productProfileVO = baseMapper.getProductProfileVOById(id);
        if(productProfileVO==null||productProfileVO.getIsDeleted()||productProfileVO.getStatus()!=1) {
            log.error("商品不存在");
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        Result<com.wddyxd.feign.pojo.userservice.usercontroller.UserProfileVO> getUserProfileVO
                = userClient.profile(productProfileVO.getUserId());
        if(getUserProfileVO.getCode()!=200||getUserProfileVO.getData()==null)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        UserProfileVO userProfileVO = new UserProfileVO();
        BeanUtil.copyProperties(getUserProfileVO.getData(),userProfileVO);
        List<Coupon> coupons =  couponService.detail(id);
        List<ProductSkuVO> productSkus = productSkuService.List(id);
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setProductProfileVO(productProfileVO);
        productDetailVO.setCoupon(coupons);
        productDetailVO.setProductSkuVO(productSkus);
        productDetailVO.setUserProfileVO(userProfileVO);
        return productDetailVO;
    }

    @Override
    @Transactional
    public void add(ProductAddDTO productAddDTO) {
        //如果规格过量则取消添加商品
        if(productAddDTO.getProductSkuDTOS().size()> CommonConstant.MAX_PRODUCT_SKU_NUM)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        //生成一个商品
        Product product = new Product();
        List<ProductSku> productSkus = new ArrayList<>();
        BeanUtil.copyProperties(productAddDTO, product);
        //判断商品分类是否合法
        ProductCategory productCategory = productCategoryMapper.selectById(productAddDTO.getCategoryId());
        if(productCategory==null||productCategory.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //远程调用获得用户名
        long userId = getCurrentUserInfoService.getCurrentUserId();
        Result<String> getUsername = userClient.getUsername(userId);
        if(getUsername.getCode()!=200||getUsername.getData()==null)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        product.setUsername(getUsername.getData());
        product.setId(IdWorker.getId());
        product.setUserId(userId);
        product.setStock(0);
        boolean isSetDefault = false;
        //处理商品规格
        for(ProductSkuDTO productSkuDTO : productAddDTO.getProductSkuDTOS()){
            ProductSku productSku = new ProductSku();
            BeanUtil.copyProperties(productSkuDTO, productSku);
            productSku.setId(IdWorker.getId());
            productSku.setProductId(product.getId());
            productSku.setSales(0);
            product.setStock(productSku.getStock()+product.getStock());
            if(productSkuDTO.getIsDefault() && !isSetDefault){
                product.setProductSkuId(productSku.getId());
                isSetDefault = true;
            }
            productSkus.add(productSku);
        }
        if(!isSetDefault)
            product.setProductSkuId(productSkus.getFirst().getId());
        baseMapper.insert(product);
        productSkuService.saveBatch(productSkus);

//        TODO在插入前在redis插入添加商品的时间戳,过期时间5秒,下次访问接口时如果查询到该redis记录则直接返回
    }

    @Override
    public void update(ProductBasicUpdateDTO productBasicUpdateDTO) {
        Product product = baseMapper.selectById(productBasicUpdateDTO.getId());
        if(product==null||product.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        ProductSku productSku = productSkuService.getById(productBasicUpdateDTO.getProductSkuId());
        if(productSku==null||productSku.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        ProductCategory productCategory = productCategoryMapper.selectById(productBasicUpdateDTO.getCategoryId());
        if(productCategory==null||productCategory.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        product.setName(productBasicUpdateDTO.getName());
        product.setDescription(productBasicUpdateDTO.getDescription());
        product.setCategoryId(productBasicUpdateDTO.getCategoryId());
        product.setProductSkuId(productBasicUpdateDTO.getProductSkuId());
        baseMapper.updateById(product);
    }

    @Override
    public void status(Long id) {
        Product product = baseMapper.selectById(id);
        if(product==null||product.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        product.setStatus(product.getStatus()==1?0:1);
        baseMapper.updateById(product);
        //TODO 异步强制取消订单
    }

    @Override
    public void delete(Long id) {

    }
}
