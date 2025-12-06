package com.wddyxd.productservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.productservice.mapper.ProductCategoryMapper;
import com.wddyxd.productservice.pojo.entity.ProductCategory;
import com.wddyxd.productservice.service.Interface.IProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 15:16
 **/
@Service
public class IProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {

    @Override
    public Page<ProductCategory> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<ProductCategory> wrapper = Wrappers.lambdaQuery(ProductCategory.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), ProductCategory::getName, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public void add(String name) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        this.save(productCategory);
    }

    @Override
    public void update(Long id, String name) {
        ProductCategory dbProductCategory = this.getById(id);
        if (dbProductCategory == null || dbProductCategory.getIsDeleted()) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        dbProductCategory.setName(name);
        baseMapper.updateById(dbProductCategory);
    }

    @Override
    public void delete(Long id) {

    }
}
