package com.wddyxd.orderservice.pojo.DTO;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-24 20:36
 **/

public class OrderDTO {
    //TODO update
    private Long id;

    private Integer status;

    private Integer payMethod;
    //add
    private Long userId;

    private Long productId;

    private Long skuId;

    private Integer quantity;

    private Long[] userCouponIds;

}
