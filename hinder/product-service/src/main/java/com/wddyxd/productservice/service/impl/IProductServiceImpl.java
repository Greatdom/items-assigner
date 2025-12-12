package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.productservice.mapper.ProductCategoryMapper;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.pojo.DTO.*;
import com.wddyxd.productservice.pojo.VO.ProductDetailVO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.pojo.entity.ProductCategory;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import com.wddyxd.productservice.service.Interface.IProductService;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Override
    public Page<ProductProfileVO> List(ProductListDTO productListDTO) {
        productListDTO.validatePageParams(productListDTO);

        Page<ProductProfileVO> page = new Page<>(productListDTO.getPageNum(), productListDTO.getPageSize());

        return baseMapper.getPageProductProfileVOManager(page, productListDTO.getSearch());
    }

    @Override
    public Page<ProductProfileVO> feed(ProductFeedDTO productFeedDTO) {
        productFeedDTO.validatePageParams(productFeedDTO);
        Page<ProductProfileVO> page = new Page<>(productFeedDTO.getPageNum(), productFeedDTO.getPageSize());
        return baseMapper.getPageProductProfileVOFeed(page, productFeedDTO.getCategoryId(), productFeedDTO.getSortColumn().getColumn(), productFeedDTO.getSortOrder().getOrder());
    }

    @Override
    public ProductDetailVO visit(Long id) {
        //       返回ProductDetailVO,这个类展示了商品详情页面的信息ProductProfileVO,
//- 用户概要指向商户UserProfileVO,优惠券指向用户领取的生效的可用优惠券CouponVO,商品规格是该商品的所有规格ProductSkuVO,
//- 用户端不应该访问被下架或删除的商品
        return null;
    }

    @Override
    public ProductDetailVO detail(Long id) {
        //        返回ProductDetailVO,这个类展示了商品详情页面的信息ProductProfileVO,
//- 用户概要指向商户UserProfileVO,优惠券指向商品的所有可用优惠券List<CouponVO>,商品规格是该商品的所有规格List<ProductSkuVO>,
//- 在商户端和后台可以访问被下架或删除的商品
        return null;
    }

    @Override
    @Transactional
    public void add(ProductAddDTO productAddDTO) {

        if(productAddDTO==null||productAddDTO.getCategoryId() == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        ProductCategory productCategory = productCategoryMapper.selectById(productAddDTO.getCategoryId());
        if(productCategory==null||productCategory.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        Product product = new Product();
        List<ProductSku> productSkus = new ArrayList<>();
        BeanUtil.copyProperties(productAddDTO, product);
        product.setId(IdWorker.getId());
        product.setStock(0);
        product.setSales(0);
        product.setPositiveComment(0);
        product.setNegativeComment(0);
        for(ProductSkuDTO productSkuDTO : productAddDTO.getProductSkuDTOS()){
            ProductSku productSku = new ProductSku();
            BeanUtil.copyProperties(productSkuDTO, productSku);
            productSku.setId(IdWorker.getId());
            productSku.setProductId(product.getId());
            product.setStock(productSku.getStock()+product.getStock());
            if(productSkuDTO.getDefault())
                product.setProductSkuId(productSku.getId());
        }

//        传入ProductAddDTO,先查询商品指向的商品分类是否存在,如果存在且没被逻辑删除则进入下一步,
//- 在事务中，商品和规格需要互相引用 ID，但它们的 ID 在插入数据库前才能生成，这造成了依赖循环,
//- 所以需要在操作数据库前手动生成ID的工作(这里采用mybatis_plus的雪花算法),
//- 然后遍历规格,为每个规格设置productId,检查价格和规格的合法性,计算规格的总库存然后加到商品的总库存字段,
//- 然后查看商品规格中第一个isDefault=true的规格,将这个规格的id赋给商品的productSkuId字段,
//- 在插入前在redis插入添加商品的时间戳,过期时间5秒,下次访问接口时如果查询到该redis记录则直接返回
//- 然后将商品和规格插入到数据库,数据库对没有处理的字段默认处理
    }

    @Override
    public void update(ProductBasicUpdateDTO productBasicUpdateDTO) {

    }

    @Override
    public void status(Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
