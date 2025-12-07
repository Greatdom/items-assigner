package com.wddyxd.userservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.encoder.PasswordEncoder;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.pojo.DTO.*;
import com.wddyxd.userservice.pojo.VO.UserDetailVO;
import com.wddyxd.userservice.pojo.VO.UserProfileVO;
import com.wddyxd.userservice.pojo.VO.UserVisitVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import com.wddyxd.userservice.service.Interface.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:12
 **/
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), User::getUsername, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public UserDetailVO detail(Long id) {
        return null;
    }

    @Override
    public UserProfileVO profile(Long id) {
        return null;
    }


    @Override
    public CurrentUserDTO me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof com.wddyxd.security.pojo.CurrentUserDTO currentUserDTO) {
            CurrentUserDTO get = new CurrentUserDTO();
            BeanUtils.copyProperties(currentUserDTO,get);
            return get;
        }else throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
    }

    @Override
    public UserVisitVO visit(Long id) {
        return null;
    }

    @Override
    public void addAdmin(CustomUserRegisterDTO customUserRegisterDTO) {
        User user = new User();
        user.setUsername(customUserRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(customUserRegisterDTO.getPassword()));
        user.setPhone(customUserRegisterDTO.getPhone());
        user.setEmail(customUserRegisterDTO.getEmail());
        user.setNickName("SUPER_ADMIN");
        baseMapper.insert(user);
    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {

    }

    @Override
    public void updatePhone(UpdatePhoneDTO updatePhoneDTO) {

    }

    @Override
    public void updateEmail(UpdateEmailDTO updateEmailDTO) {

    }

    @Override
    public void updateAvatar(UpdateAvatarDTO updateAvatarDTO) {

    }

    @Override
    public void updateNickname(UpdateNickNameDTO updateNickNameDTO) {

    }

    @Override
    public void updateGender(UpdateGenderDTO updateGenderDTO) {

    }

    @Override
    public void updateBirthday(UpdateBirthdayDTO updateBirthdayDTO) {

    }

    @Override
    public void updateRegion(UpdateRegionDTO updateRegionDTO) {

    }

    @Override
    public void updateCard(UpdateCardDTO updateCardDTO) {

    }

    @Override
    public void status(Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
