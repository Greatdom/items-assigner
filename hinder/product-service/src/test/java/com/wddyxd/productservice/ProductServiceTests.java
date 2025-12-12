package com.wddyxd.productservice;


import com.wddyxd.productservice.pojo.DTO.ProductListDTO;
import com.wddyxd.productservice.service.Interface.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 15:04
 **/

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private IProductService productService;

    @Test
    public void List(){
        System.out.println(productService.List(new ProductListDTO()));
    }

}
