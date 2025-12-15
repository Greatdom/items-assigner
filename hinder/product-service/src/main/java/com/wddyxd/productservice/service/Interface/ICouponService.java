package com.wddyxd.productservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.productservice.pojo.DTO.CouponDTO;
import com.wddyxd.productservice.pojo.entity.Coupon;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:47
 **/

public interface ICouponService extends IService<Coupon> {

    public Page<Coupon> List(SearchDTO searchDTO);

    public List<Coupon> detail(Long id);

    public List<Coupon> visit(Long id);

    public void add(CouponDTO couponDTO);

    public void update(CouponDTO couponDTO);

    public void delete(Long id);

}
