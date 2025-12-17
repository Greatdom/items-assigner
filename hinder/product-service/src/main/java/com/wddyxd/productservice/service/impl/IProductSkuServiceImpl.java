package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.mapper.ProductSkuMapper;
import com.wddyxd.productservice.pojo.DTO.ProductSkuDTO;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.pojo.entity.Coupon;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import com.wddyxd.productservice.service.Interface.IProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:43
 **/
@Service
public class IProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSku> implements IProductSkuService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductSkuVO> List(Long id) {
        return baseMapper.selectProductSkuVOByProductId(id);
    }

    @Override
    @Transactional
    public void add(ProductSkuDTO productSkuDTO) {
        // 判断商品规格数量是否超出限制,但不需要加锁
        long count = baseMapper.selectCount(new LambdaQueryWrapper<ProductSku>()
                .eq(ProductSku::getProductId, productSkuDTO.getProductId())
                .eq(ProductSku::getIsDeleted, false)
        );
        if(count> CommonConstant.MAX_PRODUCT_SKU_NUM)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        // 添加商品规格
        ProductSku productSku = new ProductSku();
        BeanUtil.copyProperties(productSkuDTO, productSku);
        // 获取商品
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getId, productSkuDTO.getProductId()));
        if(product == null||product.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        int productStock = product.getStock();
        //计算库存
        product.setStock(product.getStock()+productSku.getStock());
        // 执行乐观锁更新
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
                .eq(Product::getId, product.getId())
                .eq(Product::getStock, productStock)
                .eq(Product::getIsDeleted, false);

        int updateCount = productMapper.update(product, updateWrapper);
        //TODO 可用异步通信技术添加重试机制
        if (updateCount == 0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);

        baseMapper.insert(productSku);

    }

    @Override
    @Transactional
    public void update(ProductSkuDTO productSkuDTO) {
        ProductSku productSku = baseMapper.selectById(productSkuDTO.getId());
        if(productSku == null||productSku.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        int productSkuStock = productSku.getStock();
        boolean isUpdateStock = !Objects.equals(productSku.getStock(), productSkuDTO.getStock());
        BeanUtil.copyProperties(productSkuDTO, productSku);
        if(isUpdateStock){
            Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>()
                    .eq(Product::getId, productSkuDTO.getProductId()));
            if(product == null||product.getIsDeleted())
                throw new CustomException(ResultCodeEnum.PARAM_ERROR);
            int productStock = product.getStock();
            product.setStock(product.getStock()-productSkuStock+productSku.getStock());
            // 执行乐观锁更新
            LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
                    .eq(Product::getId, product.getId())
                    .eq(Product::getStock, productStock)
                    .eq(Product::getIsDeleted, false);
            int updateCount = productMapper.update(product, updateWrapper);
            //TODO 可用异步通信技术添加重试机制
            if (updateCount == 0)
                throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        }
        LambdaUpdateWrapper<ProductSku> updateWrapper = Wrappers.lambdaUpdate(ProductSku.class)
                .eq(ProductSku::getId, productSku.getId())
                .eq(ProductSku::getStock, productSkuStock)
                .eq(ProductSku::getIsDeleted, false);
        int updateCount = baseMapper.update(productSku, updateWrapper);
        if (updateCount == 0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductSku productSku = baseMapper.selectById(id);
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getId, productSku.getProductId()));
        if(product == null||product.getIsDeleted()){
            productSku.setIsDeleted(true);
            baseMapper.updateById(productSku);
            return;
        }
        //不能删除最后一个商品规格也不能删除默认商品规格
        long count = baseMapper.selectCount(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productSku.getProductId()));
        if(count == 1)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        if(Objects.equals(productSku.getId(), product.getProductSkuId()))
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        int productStock = product.getStock();
        product.setStock(product.getStock()-productSku.getStock());
        LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
                .eq(Product::getId, product.getId())
                .eq(Product::getStock, productStock)
                .eq(Product::getIsDeleted, false);
        int updateCount = productMapper.update(product, updateWrapper);
        //TODO 可用异步通信技术添加重试机制
        if (updateCount == 0)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        productSku.setIsDeleted(true);
        baseMapper.updateById(productSku);
    }
}
