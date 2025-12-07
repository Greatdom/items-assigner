package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.mapper.ShopCategoryMapper;
import com.wddyxd.userservice.pojo.entity.Role;
import com.wddyxd.userservice.pojo.entity.ShopCategory;
import com.wddyxd.userservice.service.Interface.IShopCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 20:02
 **/
@Service
public class IShopCategoryServiceImpl extends ServiceImpl<ShopCategoryMapper, ShopCategory> implements IShopCategoryService {

    @Override
    public Page<ShopCategory> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);
        LambdaQueryWrapper<ShopCategory> wrapper = Wrappers.lambdaQuery(ShopCategory.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), ShopCategory::getName, searchDTO.getSearch());
        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public void add(String name) {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setName(name);
        baseMapper.insert(shopCategory);
    }

    @Override
    public void update(Long id, String name) {
        ShopCategory dbShopCategory = baseMapper.selectById(id);
        if(dbShopCategory == null || dbShopCategory.getIsDeleted()) {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        dbShopCategory.setName(name);
        baseMapper.updateById(dbShopCategory);
    }

    @Override
    public void delete(Long id) {

    }
}
