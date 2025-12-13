package com.wddyxd.productservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.productservice.pojo.VO.UserCouponVO;
import com.wddyxd.productservice.pojo.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-12 08:52
 **/
@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {

    public List<UserCouponVO> listUserCouponVO(long userId);

}
