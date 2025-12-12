package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.pojo.DTO.ProductAddDTO;
import com.wddyxd.productservice.pojo.DTO.ProductBasicUpdateDTO;
import com.wddyxd.productservice.pojo.DTO.ProductFeedDTO;
import com.wddyxd.productservice.pojo.DTO.ProductListDTO;
import com.wddyxd.productservice.pojo.VO.ProductDetailVO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.service.Interface.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 12:05
 **/
@Service
public class IProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

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
        return null;
    }

    @Override
    public ProductDetailVO detail(Long id) {
        return null;
    }

    @Override
    public void add(ProductAddDTO productAddDTO) {

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
