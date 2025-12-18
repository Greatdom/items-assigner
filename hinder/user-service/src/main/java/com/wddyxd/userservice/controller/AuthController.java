package com.wddyxd.userservice.controller;


import com.wddyxd.common.paramValidateGroup.AddGroup;
import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import com.wddyxd.common.utils.Result;
import com.wddyxd.security.security.UserInfoManager;
import com.wddyxd.userservice.pojo.DTO.*;
import com.wddyxd.userservice.pojo.VO.PasswordSecurityGetterVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.service.Interface.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-28 23:36
 **/

@RestController
@RequestMapping("/user/auth")
@Tag(name = "用户认证相关接口", description = "用户注册认证相关操作")
@Validated
public class AuthController {

    @Autowired
    private IAuthService authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    @Operation(summary = "用户登录接口", description = "在前端任何客户端按下登录按钮时触发,可以用用户名/手机/邮箱进行密码登录,\n" +
            "- 同时可以用手机或邮箱进行验证码登录,通用在用户端,商户端和管理端,\n" +
            "- 需要标明是用密码还是验证码登录,需要获取用户的IP和设备和客户端,\n" +
            "- 登录成功后返回token,token有效时间是7天")
    public Result<String> login(@RequestBody LoginUserForm loginUserForm){

//        前端传入UserLoginForm,然后这个请求被springSecurity过滤器链捕捉,在被判断是登录接口后被TokenLoginFilter拦截后,从HttpRequest解析表单数据并判断登录的类型并加工表单.
//                - 加工完成后被LoginAuthenticationToken包装并交给AuthenticationManager的实现类链AuthenticationProvider,
//                - AuthenticationProvider根据LoginAuthenticationToken包装的数据调用不同的UserDetailService来获取用户信息,
//                - 调用远程接口AuthController的相关方法,首先从redis拿,拿不到就去mysql拿. 并包装到UserDetail的实现类,
//                - 然后在密码加工(目前用MD5加工密码)比较器判断密码/验证码是否吻合,最后回到TokenLoginFilter来生成token(用对称加密生成)
//                - token包装用户id,timestamp,ip,device,client并设置7天有效期,一个用户支持3个token实现最多三个设备登录.
//                - 然后将用户信息保存到redis14天有效期,最后给前端返回token.不能给被删除或封禁的用户登录.
        log.info("用户登录接口");
        return null;
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出接口", description = "按下登出按钮后触发,然后删除redis中的token信息")
    public Result<User> logout(){
//        在TokenLogoutHandler删除redis中的token信息然后返回空结果
        log.info("用户登出接口");
        return null;
    }

    @GetMapping("/phoneCodeSecurityGetter/{phone}")
    @Operation(summary = "用户登录接口的手机登录接口", description = "在用户登录接口触发时如果登录类型为phoneCode则触发, 得到手机验证码和用户信息")
    public Result<CurrentUserDTO> phoneCodeSecurityGetter(@PathVariable String phone){

//        PhoneCodeUserDetailServiceImpl调用该接口,
//                - 然后根据手机号查找数据库得到用户基本信息User来判断用户是否存在和正常使用,然后先从redis得到用户信息currentUserVO和用户的手机验证码,
//                - 拿不到用户信息就去mysql继续得到用户信息CurrentUserVO,但拿不到手机验证码就返回错误. 将数据包装到PhoneCodeSecurityGetterVO并返回
//                如果用户被封禁或被删除则直接返回错误信息
        log.info("用户登录接口的手机登录接口");
        return Result.success(authService.phoneCodeSecurityGetter(phone));
    }

    @GetMapping("/emailCodeSecurityGetter/{email}")
    @Operation(summary = "用户登录接口的邮箱登录接口", description = "在用户登录接口触发时如果登录类型为emailCode则触发, 得到邮箱验证码和用户信息")
    public Result<CurrentUserDTO> emailCodeSecurityGetter(@PathVariable String email){

//        EmailCodeUserDetailServiceImpl调用该接口,
//                - 然后根据邮箱查找数据库得到用户基本信息User来判断用户是否存在和正常使用,然后先从redis得到用户信息currentUserVO和用户的邮箱验证码,
//                - 拿不到用户信息就去mysql继续得到用户信息CurrentUserVO,但拿不到邮箱验证码就返回错误. 将数据包装到EmailCodeSecurityGetterVO并返回
//                如果用户被封禁或被删除则直接返回错误信息
        log.info("用户登录接口的邮箱登录接口");
        return Result.success(authService.emailCodeSecurityGetter(email));
    }

    @GetMapping("/passwordSecurityGetter/{username}")
    @Operation(summary = "用户登录接口的密码登录接口", description = "在用户登录接口触发时如果登录类型为password则触发, 得到用户密码和用户信息")
    public Result<PasswordSecurityGetterVO> passwordSecurityGetter(@PathVariable String username){

//        PasswordUserDetailServiceImpl调用该接口,在username这个参数进行正则表达式校验,判断是用户名/手机/邮箱,
//                - 然后用username参数查找数据库得到用户基本信息User来得到密码,然后先从redis得到用户信息currentUserVO,
//                - 拿不到就去mysql继续得到用户信息CurrentUserVO. 将数据包装到PasswordSecurityGetterVO并返回
//                如果用户被封禁或被删除则直接返回错误信息
        log.info("用户登录接口的密码登录接口");
        return Result.success(authService.passwordSecurityGetter(username));
    }

    @GetMapping("/phoneCode/{phone}")
    @Operation(summary = "获取手机验证码接口", description = "获取手机验证码接口,在手机登录,修改个人信息时调用")
    public Result<Void> phoneCode(@PathVariable @NotBlank(message = "手机号不能为空") String phone) {

//        前端设置用户60秒访问一次该接口,
//- 获取手机号,判断合法性后发送手机验证码,同时要根据手机号对接口进行基于 Redis 的计数器限流和sentinel限流,
//- 可以用短信服务商 API 将验证码发送给用户,但这里暂时简化为后端生成并打印随机验证码,
//- 在redis查询到手机号的访问信息则直接返回提示,
//- 验证码保存在redis中,过期时间为15分钟,然后在redis记录该手机号的访问信息,过期时间为60秒,返回空值
        log.info("获取手机验证码接口");
        authService.phoneCode(phone);
        return Result.success();
    }

    @GetMapping("/emailCode/{email}")
    @Operation(summary = "获取邮箱验证码接口", description = "获取邮箱验证码接口,在邮箱登录,修改个人信息时调用")
    public Result<Void> emailCode(@PathVariable @NotBlank(message = "邮箱不能为空") String email) {

//        前端设置用户60秒访问一次该接口,
//- 获取邮箱,判断合法性后发送邮箱验证码,同时要根据邮箱对接口进行基于 Redis 的计数器限流和sentinel限流,
//- 可以用第三方邮箱服务商将验证码发送给用户,但这里简化为编写邮箱发送工具类,根据SMTP协议发送验证码,
//- 在redis查询到邮箱的访问信息则直接返回提示,
//- 验证码保存在redis中,过期时间为15分钟,然后在redis记录该邮箱的访问信息,过期时间为60秒,返回空值
        log.info("获取邮箱验证码接口");
        authService.emailCode(email);
        return Result.success();
    }

    @PostMapping("/customUserRegister")
    @Operation(summary = "用户端用户注册接口", description = "用户端的注册接口")
    public Result<Void> customUserRegister(@Validated(AddGroup.class) @RequestBody CustomUserRegisterDTO customUserRegisterDTO){
//        首先判断用户名,手机号和邮箱的合法性,得到参数后分别根据用户名,手机号和邮箱获取user表的一条数据,
//                - 如果三份数据都是null则注册用户,分配ROLE_NEW_USER角色,创建user表,关联user_role表,创建user_detail表并填充默认数据
//                - 其他情况返回错误
//        - 其中,用加密算法(这里用MD5)加密密码,同时将事先存储到redis的phoneCode和emailCode提取来验证手机和邮箱的有效性
        log.info("用户端用户注册接口");
        authService.customUserRegister(customUserRegisterDTO);
        return Result.success();
    }

    @PostMapping("/merchantRegister")
    @Operation(summary = "商户端商户注册接口", description = "商户端的注册接口")
    public Result<Void> merchantRegister(@Validated(AddGroup.class) @RequestBody MerchantRegisterDTO merchantRegisterDTO){
//        首先判断用户名,手机号和邮箱的合法性,得到参数后分别根据用户名,手机号和邮箱获取user表的一条数据,
//                - 如果三份数据都是null则调用用户端注册接口
//                        - 如果三份数据都指向同一个用户而且没有被分配商户相关角色则将该用户分配ROLE_NEW_MERCHANT角色,
//                - 并创建merchant_supplement表,注意新注册的商店是关店状态
//                - 其他情况返回错误
//                        - 其中,用加密算法(这里用MD5)加密密码,同时将事先存储到redis的phoneCode和emailCode提取来验证手机和邮箱的有效性
        log.info("商户端商户注册接口");
        authService.merchantRegister(merchantRegisterDTO);
        return Result.success();
    }

    @PostMapping("/rebuildPassword")
    @Operation(summary = "根据验证码找回密码接口", description = "支持根据手机验证码为账号找回密码")
    public Result<Void> rebuildPassword(@Validated(UpdateGroup.class) @RequestBody RebuildPasswordDTO rebuildPasswordDTO){
//        需要手机和验证码和新密码,后端会判断手机和验证码是否合法且吻合.
//- 成功更改密码后时redis存储5秒过期的时间戳,过期时间内不能重新更改密码,更改密码后强制在redis的当前用户的token删除
        log.info("根据验证码找回密码接口");
        authService.rebuildPassword(rebuildPasswordDTO);
        return Result.success();
    }

}
