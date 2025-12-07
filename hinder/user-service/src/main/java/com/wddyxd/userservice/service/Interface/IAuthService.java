package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.userservice.pojo.DTO.CustomUserRegisterDTO;
import com.wddyxd.userservice.pojo.DTO.MerchantRegisterDTO;
import com.wddyxd.userservice.pojo.DTO.RebuildPasswordDTO;
import com.wddyxd.userservice.pojo.VO.EmailCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PasswordSecurityGetterVO;
import com.wddyxd.userservice.pojo.VO.PhoneCodeSecurityGetterVO;
import com.wddyxd.userservice.pojo.entity.User;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-03 16:03
 **/

public interface IAuthService extends IService<User> {

    public PasswordSecurityGetterVO passwordSecurityGetter(String username);

    public PhoneCodeSecurityGetterVO phoneCodeSecurityGetter(String phone);

    public EmailCodeSecurityGetterVO emailCodeSecurityGetter(String email);

    public void phoneCode(String phone);

    public void emailCode(String email);

    public void customUserRegister(CustomUserRegisterDTO customUserRegisterDTO);

    public void merchantRegister(MerchantRegisterDTO merchantRegisterDTO);

    public void rebuildPassword(RebuildPasswordDTO rebuildPasswordDTO);

}
