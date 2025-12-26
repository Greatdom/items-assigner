package com.wddyxd.productservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.productservice.pojo.VO.UserCouponVO;
import com.wddyxd.productservice.pojo.entity.UserCoupon;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:53
 **/

public interface IUserCouponService extends IService<UserCoupon> {

    public List<UserCouponVO> List();

    public void add(Long id);

    public List<Long> consume(Long[] couponIds,Long orderId);

    public void destroy(Long id);

}
