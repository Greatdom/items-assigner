package com.wddyxd.productservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.productservice.pojo.entity.ProductCategory;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 15:16
 **/

public interface IProductCategoryService extends IService<ProductCategory> {

    public Page<ProductCategory> List(SearchDTO searchDTO);

    public void add(String name);

    public void update(Long id, String name);

    public void delete(Long id);

}
