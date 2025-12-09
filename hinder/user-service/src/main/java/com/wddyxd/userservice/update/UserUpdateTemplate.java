package com.wddyxd.userservice.update;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.BaseUserUpdateDTO;
import com.wddyxd.userservice.pojo.entity.MerchantSupplement;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserDetail;
import com.wddyxd.userservice.pojo.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-08 20:03
 **/

@Component
public class UserUpdateTemplate {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private MerchantSupplementMapper merchantSupplementMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //通用多表更新流程
    @Transactional
    public <T extends BaseUserUpdateDTO> void update(T dto, UserUpdateStrategy<T> strategy){
        //TODO 作为仅可自己触发的业务,应该用从token获取的id来代替参数的id
        if(dto == null || dto.getId() == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //按需加载多表数据
        UserRelatedData relatedData = loadRelatedData(dto.getId(), strategy.needLoadTables());

        if(!relatedData.hasUser())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);

        strategy.validate(dto, relatedData);

        strategy.update(dto, relatedData);

        postProcess(dto, relatedData, strategy);

    }
    //按需加载关联表数据
    private UserRelatedData loadRelatedData(Long userId, List<UserUpdateStrategy.RelatedTableType> needLoadTables){
        UserRelatedData relatedData = new UserRelatedData();
        //按需加载表
        if(needLoadTables.contains(UserUpdateStrategy.RelatedTableType.USER)){
            relatedData.setUser(userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getId, userId)
                    .eq(User::getIsDeleted, 0)));
        }
        if(needLoadTables.contains(UserUpdateStrategy.RelatedTableType.USER_DETAIL)){
            UserDetail userDetail = userDetailMapper.selectByUserId(userId);
            relatedData.setUserDetail(userDetail);
        }
        if(needLoadTables.contains(UserUpdateStrategy.RelatedTableType.MERCHANT_SUPPLEMENT)){
            MerchantSupplement merchantSupplement = merchantSupplementMapper.selectByUserId(userId);
            relatedData.setMerchantSupplement(merchantSupplement);
        }

        return relatedData;
    }

    private <T extends BaseUserUpdateDTO> void postProcess(T dto, UserRelatedData relatedData,UserUpdateStrategy<T> strategy){
        //比如设置redis时间戳来解决幂等性问题
    }

}
