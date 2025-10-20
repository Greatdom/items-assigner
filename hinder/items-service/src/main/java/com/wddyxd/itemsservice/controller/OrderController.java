package com.wddyxd.itemsservice.controller;


import com.wddyxd.common.utils.Result;
import com.wddyxd.itemsservice.pojo.dto.OrderDTO;
import com.wddyxd.itemsservice.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:53
 **/

@RestController
@RequestMapping("/items")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/get/{id}")
    public Result<OrderDTO> get(@PathVariable Long id){
        return orderService.getDTOById(id);
    }
}
