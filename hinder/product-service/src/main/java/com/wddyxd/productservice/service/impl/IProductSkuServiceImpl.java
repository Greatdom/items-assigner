package com.wddyxd.productservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.mapper.ProductSkuMapper;
import com.wddyxd.productservice.pojo.DTO.ProductSkuDTO;
import com.wddyxd.productservice.pojo.VO.ProductSkuVO;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.pojo.entity.ProductSku;
import com.wddyxd.productservice.service.Interface.IProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        ProductSkuDTO.addValidations(productSkuDTO);
        ProductSku productSku = new ProductSku();
        //TODO 同个商品不能超过30个规格
        BeanUtil.copyProperties(productSkuDTO, productSku);
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getId, productSkuDTO.getProductId()));
        if(product == null||product.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        product.setStock(product.getStock()+productSku.getStock());
        productMapper.updateById(product);
        baseMapper.insert(productSku);
    }

    @Override
    @Transactional
    public void update(ProductSkuDTO productSkuDTO) {
        ProductSkuDTO.updateValidations(productSkuDTO);
        ProductSku productSku = new ProductSku();
        BeanUtil.copyProperties(productSkuDTO, productSku);
        Product product = productMapper.selectOne(new LambdaQueryWrapper<Product>().eq(Product::getId, productSkuDTO.getProductId()));
        if(product == null||product.getIsDeleted())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        product.setStock(product.getStock()-productSku.getStock());
        productMapper.updateById(product);
        baseMapper.updateById(productSku);
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
        long count = baseMapper.selectCount(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productSku.getProductId()));
        if(count == 1)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        product.setStock(product.getStock()-productSku.getStock());
        productMapper.updateById(product);
        productSku.setIsDeleted(true);
        baseMapper.updateById(productSku);
    }
}
