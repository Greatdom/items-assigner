package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.common.utils.Result;
import com.wddyxd.userservice.mapper.MerchantSupplementMapper;
import com.wddyxd.userservice.mapper.UserDetailMapper;
import com.wddyxd.userservice.mapper.UserMapper;
import com.wddyxd.userservice.mapper.UserRoleMapper;
import com.wddyxd.userservice.pojo.DTO.UserRelatedData;
import com.wddyxd.userservice.pojo.DTO.update.BaseUserUpdateDTO;
import com.wddyxd.userservice.pojo.entity.User;
import com.wddyxd.userservice.pojo.entity.UserDetail;
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
public abstract class UserUpdateTemplate {

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
    public <T extends BaseUserUpdateDTO> Result<Void> update(T dto, UserUpdateStrategy<T> strategy){
        if(dto == null || dto.getId() == null)
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        //按需加载多表数据
        UserRelatedData relatedData = loadRelatedData(dto.getId(), strategy.needLoadTables());

        if(relatedData == null || !relatedData.hasUser())
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);

        strategy.validate(dto, relatedData);

        strategy.update(dto, relatedData);

        postProcess(dto, relatedData, strategy);

        return Result.success();
    }
    //按需加载关联表数据
    private UserRelatedData loadRelatedData(Long userId, List<UserUpdateStrategy.RelatedTableType> needLoadTables){
        UserRelatedData relatedData = new UserRelatedData();
        User user = userMapper.selectById(userId);
        //按需加载其他表
        return null;
    }

    private <T extends BaseUserUpdateDTO> void postProcess(T dto, UserRelatedData relatedData,UserUpdateStrategy<T> strategy){
        //比如设置redis时间戳来解决幂等性问题
    }

}
