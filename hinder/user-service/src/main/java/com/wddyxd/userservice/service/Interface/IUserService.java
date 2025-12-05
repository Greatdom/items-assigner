package com.wddyxd.userservice.service.Interface;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wddyxd.common.pojo.SearchDTO;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.pojo.DTO.*;
import com.wddyxd.userservice.pojo.VO.UserDetailVO;
import com.wddyxd.userservice.pojo.VO.UserProfileVO;
import com.wddyxd.userservice.pojo.VO.UserVisitVO;
import com.wddyxd.userservice.pojo.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: 新建文件夹
 * @description: description
 * @author: wddyxd
 * @create: 2025-10-08 05:10
 **/

public interface IUserService extends IService<User> {

    public Page<User> List(SearchDTO searchDTO);

    public UserDetailVO detail(Long id);

    public UserProfileVO profile(Long id);

    public CurrentUserDTO me();

    public UserVisitVO visit(Long id);

    public void addAdmin(CustomUserRegisterDTO customUserRegisterDTO);

    public void updatePassword(UpdatePasswordDTO updatePasswordDTO);

    public void updatePhone(UpdatePhoneDTO updatePhoneDTO);

    public void updateEmail(UpdateEmailDTO updateEmailDTO);

    public void updateAvatar(UpdateAvatarDTO updateAvatarDTO);

    public void updateNickname(UpdateNickNameDTO updateNickNameDTO);

    public void updateGender(UpdateGenderDTO updateGenderDTO);

    public void updateBirthday(UpdateBirthdayDTO updateBirthdayDTO);

    public void updateRegion(UpdateRegionDTO updateRegionDTO);

    public void updateCard(UpdateCardDTO updateCardDTO);

    public void status(Long id);

    public void delete(Long id);

}
