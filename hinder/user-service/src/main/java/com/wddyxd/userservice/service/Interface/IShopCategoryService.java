package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.pojo.entity.ShopCategory;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 20:01
 **/

public interface IShopCategoryService extends IService<ShopCategory> {

    public Page<ShopCategory> List(SearchDTO searchDTO);

    public void add(String name);

    public void update(Long id, String name);

    public void delete(Long id);

}
