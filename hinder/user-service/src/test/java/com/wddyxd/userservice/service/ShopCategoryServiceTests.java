package com.wddyxd.userservice.service;


import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.service.Interface.IShopCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 20:13
 **/

@SpringBootTest
public class ShopCategoryServiceTests {

    @Autowired
    private IShopCategoryService shopCategoryService;

    @Test
    public void List() {
        shopCategoryService.List(new SearchDTO());
    }

    @Test
    public void add() {
        shopCategoryService.add("默认分类");
        shopCategoryService.add("书店");
        shopCategoryService.add("食品");
    }

    @Test
    public void update() {
        shopCategoryService.update(-1L, "测试");
    }

    @Test
    public void delete() {
        shopCategoryService.delete(-1L);
    }

}
