package com.wddyxd.userservice.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.constant.RoleConstant;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.security.service.GetCurrentUserInfoService;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.*;
import com.wddyxd.userservice.pojo.DTO.update.*;
import com.wddyxd.userservice.pojo.VO.UserDetailVO;
import com.wddyxd.userservice.pojo.VO.UserProfileVO;
import com.wddyxd.userservice.pojo.VO.UserVisitVO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.service.Interface.IAuthService;
import com.wddyxd.userservice.service.Interface.IUserRoleService;
import com.wddyxd.userservice.service.Interface.IUserService;
import com.wddyxd.userservice.update.UserUpdateStrategy;
import com.wddyxd.userservice.update.UserUpdateStrategyFactory;
import com.wddyxd.userservice.update.UserUpdateTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:12
 **/
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private GetCurrentUserInfoService getCurrentUserInfoService;

    @Autowired
    private UserUpdateStrategyFactory userUpdateStrategyFactory;

    @Autowired
    private UserUpdateTemplate updateTemplate;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public String getUsername(Long id) {
        User user = baseMapper.selectById(id);
        if(user == null||user.getIsDeleted())
            return null;
        return user.getUsername();
    }

    @Override
    public Page<User> List(SearchDTO searchDTO) {
        searchDTO.validatePageParams(searchDTO);

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
                .like(StringUtils.hasText(searchDTO.getSearch()), User::getUsername, searchDTO.getSearch());

        return this.page(new Page<>(searchDTO.getPageNum(), searchDTO.getPageSize()), wrapper);
    }

    @Override
    public UserDetailVO detail(Long id) {

        //获取客户端

        //获取用户详细信息

        return null;
    }

    @Override
    public List<UserProfileVO> profiles(Long[] ids) {
        return null;
    }

    @Override
    public UserProfileVO profile(Long id) {
        //因为其他用户也会调用这个方法,所以保留id参数
        User user = this.getById(id);
        return BeanUtil.copyProperties(user, UserProfileVO.class);
    }


    @Override
    public CurrentUserDTO me() {
        return BeanUtil.copyProperties(getCurrentUserInfoService.getCurrentUserInfo(), CurrentUserDTO.class);
    }

    @Override
    public UserVisitVO visit(Long id) {
        return null;
    }

    @Override
    public void addAdmin(CustomUserRegisterDTO customUserRegisterDTO) {
        MerchantRegisterDTO merchantRegisterDTO = BeanUtil.copyProperties(customUserRegisterDTO, MerchantRegisterDTO.class);
        long userId = authService.merchantRegister(merchantRegisterDTO);
        userRoleService.insertUserRoleWithDeleteSameGroup(userId, RoleConstant.ROLE_NEW_ADMIN.getId());


    }

    @Override
    public void updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        UserUpdateStrategy<UpdatePasswordDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdatePasswordDTO.class);
        updateTemplate.update(updatePasswordDTO, strategy);
    }

    @Override
    public void updatePhone(UpdatePhoneDTO updatePhoneDTO) {
        UserUpdateStrategy<UpdatePhoneDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdatePhoneDTO.class);
        updateTemplate.update(updatePhoneDTO, strategy);
    }

    @Override
    public void updateEmail(UpdateEmailDTO updateEmailDTO) {
        UserUpdateStrategy<UpdateEmailDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateEmailDTO.class);
        updateTemplate.update(updateEmailDTO, strategy);
    }

    @Override
    public void updateAvatar(UpdateAvatarDTO updateAvatarDTO) {
        UserUpdateStrategy<UpdateAvatarDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateAvatarDTO.class);
        updateTemplate.update(updateAvatarDTO, strategy);
    }

    @Override
    public void updateNickname(UpdateNickNameDTO updateNickNameDTO) {
        UserUpdateStrategy<UpdateNickNameDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateNickNameDTO.class);
        updateTemplate.update(updateNickNameDTO, strategy);
    }

    @Override
    public void updateGender(UpdateGenderDTO updateGenderDTO) {
        UserUpdateStrategy<UpdateGenderDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateGenderDTO.class);
        updateTemplate.update(updateGenderDTO, strategy);
    }

    @Override
    public void updateBirthday(UpdateBirthdayDTO updateBirthdayDTO) {
        UserUpdateStrategy<UpdateBirthdayDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateBirthdayDTO.class);
        updateTemplate.update(updateBirthdayDTO, strategy);
    }

    @Override
    public void updateRegion(UpdateRegionDTO updateRegionDTO) {
        UserUpdateStrategy<UpdateRegionDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateRegionDTO.class);
        updateTemplate.update(updateRegionDTO, strategy);
    }

    @Override
    public void updateCard(UpdateCardDTO updateCardDTO) {
        UserUpdateStrategy<UpdateCardDTO> strategy = userUpdateStrategyFactory.getStrategy(UpdateCardDTO.class);
        updateTemplate.update(updateCardDTO, strategy);
    }

    @Override
    public void status(Long id) {
        //TODO 用策略类重构代码
        User user = this.getById(id);
        if(user==null||user.getIsDeleted()==true)
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        user.setStatus(user.getStatus()==1?0:1);
        baseMapper.updateById(user);
    }

    @Override
    public void delete(Long id) {

    }
}
