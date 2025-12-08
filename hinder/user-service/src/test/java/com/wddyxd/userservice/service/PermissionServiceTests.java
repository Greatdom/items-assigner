package com.wddyxd.userservice.service;


import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.userservice.service.Interface.IPermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-05 09:48
 **/
@SpringBootTest
public class PermissionServiceTests {

    @Autowired
    private IPermissionService permissionService;

    @Test
    public void List(){
        permissionService.List(new SearchDTO());
    }

    @Test
    public void assign(){
        Long[] permissionIds = {
                1996964801054089217L, // 查询用户
                1996964801700012033L, // 增加用户
                1996964801700012034L, // 更新用户
                1996964801700012035L, // 删除用户
                1996964801700012036L, // 查询角色
                1996964801700012037L, // 增加角色
                1996964801767120897L, // 更新角色
                1996964801767120898L, // 删除角色
                1996964801767120899L, // 查询权限
                1996964801767120900L, // 增加权限
                1996964801767120901L, // 更新权限
                1996964801767120902L, // 删除权限
                1996964801767120903L, // 查询店铺分类
                1996964801834229762L, // 增加店铺分类
                1996964801834229763L, // 更新店铺分类
                1996964801834229764L, // 删除店铺分类
                1996964801834229765L, // 查询商品分类
                1996964801834229766L, // 增加商品分类
                1996964801834229767L, // 更新商品分类
                1996964801901338626L, // 删除商品分类
                1996964801901338627L, // 查询商品
                1996964801901338628L, // 增加商品
                1996964801901338629L, // 更新商品
                1996964801901338630L, // 删除商品
                1996964801901338631L, // 查询订单
                1996964801901338632L, // 增加订单
                1996964801964253186L, // 更新订单
                1996964801964253187L, // 查询优惠券
                1996964801964253188L, // 增加优惠券
                1996964801964253189L, // 更新优惠券
                1996964801964253190L, // 删除优惠券
                1996964801964253191L, // 查询订单状态日志
                1996964801964253192L, // 增加订单状态日志
                1996964801964253193L, // 更新订单状态日志
                1996964802031362050L, // 查询用户优惠券
                1996964802031362051L, // 增加用户优惠券
                1996964802031362052L, // 更新用户优惠券
                1996964802031362053L, // 删除用户优惠券
                1996964802031362054L  // 查询财务流水
        };

        permissionService.assign(RoleConstant.ROLE_SUPER_ADMIN.getId(),permissionIds);
    }

    @Test
    public void add(){
        String[][] permissionArr = {
                // user 模块
                {"查询用户", "user.list"},
                {"增加用户", "user.add"},
                {"更新用户", "user.update"},
                {"删除用户", "user.delete"},
                // role 模块
                {"查询角色", "role.list"},
                {"增加角色", "role.add"},
                {"更新角色", "role.update"},
                {"删除角色", "role.delete"},
                // permission 模块
                {"查询权限", "permission.list"},
                {"增加权限", "permission.add"},
                {"更新权限", "permission.update"},
                {"删除权限", "permission.delete"},
                // shopCategory 模块
                {"查询店铺分类", "shopCategory.list"},
                {"增加店铺分类", "shopCategory.add"},
                {"更新店铺分类", "shopCategory.update"},
                {"删除店铺分类", "shopCategory.delete"},
                // productCategory 模块
                {"查询商品分类", "productCategory.list"},
                {"增加商品分类", "productCategory.add"},
                {"更新商品分类", "productCategory.update"},
                {"删除商品分类", "productCategory.delete"},
                // product 模块
                {"查询商品", "product.list"},
                {"增加商品", "product.add"},
                {"更新商品", "product.update"},
                {"删除商品", "product.delete"},
                // order 模块
                {"查询订单", "order.list"},
                {"增加订单", "order.add"},
                {"更新订单", "order.update"},
                // coupon 模块
                {"查询优惠券", "coupon.list"},
                {"增加优惠券", "coupon.add"},
                {"更新优惠券", "coupon.update"},
                {"删除优惠券", "coupon.delete"},
                // orderStatusLog 模块
                {"查询订单状态日志", "orderStatusLog.list"},
                {"增加订单状态日志", "orderStatusLog.add"},
                {"更新订单状态日志", "orderStatusLog.update"},
                // userCoupon 模块
                {"查询用户优惠券", "userCoupon.list"},
                {"增加用户优惠券", "userCoupon.add"},
                {"更新用户优惠券", "userCoupon.update"},
                {"删除用户优惠券", "userCoupon.delete"},
                // financialFlow 模块
                {"查询财务流水", "financialFlow.list"}
        };
        for (String[] permission : permissionArr) {
            String name = permission[0];
            String value = permission[1];
            System.out.println(name);
            permissionService.add(name, value);
        }
    }

    @Test
    public void update(){
        permissionService.update(-1L,"test","test");
    }

    @Test
    public void delete(){
        permissionService.delete(-1L);
    }

}
