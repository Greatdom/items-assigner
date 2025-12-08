package com.wddyxd.userservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wddyxd.userservice.pojo.entity.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 13:28
 **/

public interface UserAddressMapper extends BaseMapper<UserAddress> {

    boolean checkAllIdsExistAndNotDeleted(@Param("ids") List<Long> ids);

}
