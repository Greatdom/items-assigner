package com.wddyxd.productservice;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-16 15:17
 **/

import com.wddyxd.productservice.service.Interface.IProductSkuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductSkuServiceTests {

    @Autowired
    private IProductSkuService productSkuService;

    @Test
    public void List(){
        productSkuService.List(2000808873962766337L);
    }

}
