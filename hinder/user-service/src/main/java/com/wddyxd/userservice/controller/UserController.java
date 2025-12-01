package com.wddyxd.userservice.controller;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.VO.UserDetailVO;
import com.wddyxd.userservice.pojo.VO.UserProfileVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.DTO.CurrentUserDTO;
import com.wddyxd.userservice.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @program: items-assigner
 * @description: 用户接口控制器
 * @author: wddyxd
 * @create: 2025-10-20 20:52
 **/

@RestController
@RequestMapping("/user/user")
@Tag(name = "用户控制器", description = "用户管理相关接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user.list')")
    //需要user.list权限
    @Operation(summary = "分页获取用户列表接口", description = "在管理员的用户管理主界面查看所有用户")
    public Result<?> selectAll(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String search){

//        在管理员的用户管理主界面查看所有用户,支持根据关键字在mysql为用户名,昵称建立索引以支持关键字搜索
//                - 返回List<User>并由PageResult包装,对敏感信息进行脱敏处理
//                - 注意无论用户是否被逻辑删除或被封禁都应该被被查到

        return userService.selectAll(pageNum, pageSize, search);
    }

    @GetMapping("/detail/{id}")
    //需要user.list权限而且(参数的id等于访问者的id或者访问者是管理员)
    @Operation(summary = "分页获取用户列表接口", description = "在用户端点击头像跳转到用户设置界面,后台或商户端前往个人中心," +
            "管理员在用户管理点击用户可触发该接口,查询该用户详细信息")
    public Result<UserDetailVO> detail(@PathVariable Long id){

//        返回UserDetailVO,其中必携带BasicUserDetailVO和CurrentUserDTO,如果是商户且在商户端额外携带MerchantDetailVO,
//- 如果是管理员查看用户详细信息则额外携带List<userAddressVO>,MerchantDetailVO,
//        开发前期默认返回完全数据,对敏感信息进行脱敏处理,
//- 注意无论该用户是否被逻辑删除都应该被被查到,但不应该查到被删除的子信息
    throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @GetMapping("/profile/{id}")
    //需要user.list权限而且(参数的id等于访问者的id或者访问者是管理员)
    @Operation(summary = "用户概要接口", description = "网站通用的表示某用户的接口,常被其他需要携带用户信息返回的接口调用")
    public Result<UserProfileVO> profile(@PathVariable Long id){

//        查询user表获取用户概要,返回UserProfileVO
//- 访问被删除的用户则返回空用户
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    @GetMapping("/me")
    //所有用户登录后可访问
    @Operation(summary = "获取自己的认证信息接口", description = "获取自己的认证信息,在登录成功后每次跳转页面都会访问该接口")
    public Result<CurrentUserDTO> me(){
//            访问接口时被spring-security的过滤器链拦截,
//            - 其中TokenAuthFilter会解析token并根据token信息从redis获得用户信息然后将信息包装到SecurityContextHolder,得不到信息就返回异常,
//            - (随着开发的进行,token存储的信息会得到充分利用,比如token存储的客户端可以让token不能跨端使用,token存储的ip实现ip请求限流)
//            - 然后从SecurityContextHolder获得用户信息并返回CurrentUserDTO
//            - 只需返回没有被逻辑删除的数据
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }

    //添加用户
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('user.add')")
    public Result<User> add(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }

    //更改用户信息
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user.update')")
    public Result<User> update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('user.remove')")
    public Result<User> delete(@PathVariable Long id){
        //TODO 删除用户
        throw new CustomException(ResultCodeEnum.FUNCTION_ERROR);
    }
}
