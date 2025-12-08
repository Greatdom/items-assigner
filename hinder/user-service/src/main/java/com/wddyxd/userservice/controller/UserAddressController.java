package com.wddyxd.userservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.DTO.UserAddressDTO;
import com.wddyxd.userservice.pojo.entity.UserAddress;
import com.wddyxd.userservice.service.Interface.IUserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-01 16:40
 **/
@RestController
@RequestMapping("/user/userAddress")
@Tag(name = "用户地址簿控制器", description = "用户地址簿相关接口")
public class UserAddressController {

    @Autowired
    private IUserAddressService userAddressService;

    @GetMapping("/list/{id}")
    //访问者的id等于参数的id
    @Operation(summary = "获取个人用户地址簿", description = "获取个人用户地址簿")
    public Result<List<UserAddress>> list(@PathVariable Long id){
//        在用户端个人中心或后台的用户管理可查询用户端地址簿,返回List<UserAddressDTO>
//- 只需返回没有被逻辑删除的地址
        return Result.success(userAddressService.List(id));
    }

    @PostMapping("/add")
    //访问者的id等于参数的id
    @Operation(summary = "新增地址簿接口", description = "新增地址簿,仅允许用户添加地址")
    public Result<Void> add(@RequestBody UserAddressDTO userAddressDTO){
//        传入UserAddressDTO,一个用户最多添加5个正常状态的地址簿,
//- 如果将地址设为默认地址,则将之前默认地址设为非默认地址
//- 用redis存储添加的时间戳,过期5秒,redis数据存在期间不能添加数据
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @PutMapping("/update")
    //访问者的id等于参数的id
    @Operation(summary = "修改地址簿接口", description = "修改地址簿")
    public Result<Void> update(@RequestBody List<UserAddressDTO> updatePasswordDTOS){
//        传入List<UserAddressDTO>,注意只将第一个默认地址设为默认地址
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    //访问者的id等于参数的id
    @Operation(summary = "删除地址簿接口", description = "删除地址簿")
    public Result<Void> delete(@PathVariable Long id){
//       逻辑删除地址簿
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

}
