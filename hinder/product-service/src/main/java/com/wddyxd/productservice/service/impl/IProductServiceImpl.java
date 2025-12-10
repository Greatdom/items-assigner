package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.productservice.mapper.ProductMapper;
import com.wddyxd.productservice.pojo.DTO.ProductAddDTO;
import com.wddyxd.productservice.pojo.DTO.ProductBasicUpdateDTO;
import com.wddyxd.productservice.pojo.DTO.ProductFeedDTO;
import com.wddyxd.productservice.pojo.DTO.ProductListDTO;
import com.wddyxd.productservice.pojo.VO.ProductProfileVO;
import com.wddyxd.productservice.pojo.entity.Product;
import com.wddyxd.productservice.service.Interface.IProductService;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-10 12:05
 **/

public class IProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    public ProductProfileVO List(ProductListDTO productListDTO) {
        return null;
    }

    @Override
    public ProductProfileVO feed(ProductFeedDTO productFeedDTO) {
        return null;
    }

    @Override
    public ProductProfileVO visit(Long id) {
        return null;
    }

    @Override
    public ProductProfileVO detail(Long id) {
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
