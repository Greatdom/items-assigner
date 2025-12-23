package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.pojo.entity.UserAddress;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 13:28
 **/

public interface IUserAddressService extends IService<UserAddress> {

    public List<UserAddress> List();

    public void add(UserAddressDTO userAddressDTO);

    public void update(UserAddressDTO userAddressDTO);

    public void assign(Long addressId);

    public void delete(Long id);

}
