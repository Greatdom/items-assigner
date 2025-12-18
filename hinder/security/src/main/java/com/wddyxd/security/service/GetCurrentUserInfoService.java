package com.wddyxd.security.service;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.security.pojo.CurrentUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 17:56
 **/
@Component
public class GetCurrentUserInfoService {

    public CurrentUserDTO getCurrentUserInfo(){
        return getCurrentUserDTO();
    }

    public Long getCurrentUserId(){
        return getCurrentUserInfo().getId();
    }

    public List<String> getCurrentUserRoles(){
        return getCurrentUserInfo().getRoles();
    }

    private CurrentUserDTO getCurrentUserDTO(){
        CurrentUserDTO get;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CurrentUserDTO currentUserDTO) {
            get = currentUserDTO;
        }else throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        return get;
    }

}
