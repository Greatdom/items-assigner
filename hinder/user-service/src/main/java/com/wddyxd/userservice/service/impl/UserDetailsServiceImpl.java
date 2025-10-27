package com.wddyxd.userservice.service.impl;

import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.security.pojo.CurrentUserInfo;
import com.wddyxd.security.pojo.LoginUserForm;
import com.wddyxd.security.pojo.SecurityUser;
import com.wddyxd.userservice.pojo.User;
import com.wddyxd.userservice.pojo.dto.CurrentUserDTO;
import com.wddyxd.userservice.service.IPermissionsService;
import com.wddyxd.userservice.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionsService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询数据
        User user = userService.selectByUsername(username);
        //判断
        if(user == null) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        Long id = user.getId();

        LoginUserForm loginUserForm = new LoginUserForm();
        BeanUtils.copyProperties(user,loginUserForm);

        CurrentUserDTO dto = userService.getUserInfo(id);
        CurrentUserInfo currentUserInfo = new CurrentUserInfo();
        BeanUtils.copyProperties(dto,currentUserInfo);

        return new SecurityUser(loginUserForm,currentUserInfo);
    }
}
