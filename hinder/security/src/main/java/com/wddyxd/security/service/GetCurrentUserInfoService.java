package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.security.pojo.CurrentUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 17:56
 **/
@Component
public class GetCurrentUserInfoService {

    public CurrentUserDTO getCurrentUserInfo(){
        CurrentUserDTO get;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDTO currentUserDTO) {
            get = currentUserDTO;
        }else throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        return get;
    }

    public Long getCurrentUserId(){
        CurrentUserDTO get;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDTO currentUserDTO) {
            get = currentUserDTO;
        }else throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        return get.getId();
    }

}
