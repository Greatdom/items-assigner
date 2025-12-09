package com.wddyxd.userservice.update;


import com.wddyxd.common.constant.ResultCodeEnum;
import com.wddyxd.common.exceptionhandler.CustomException;
import com.wddyxd.userservice.pojo.DTO.update.BaseUserUpdateDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-09 20:48
 **/

@Component
public class UserUpdateStrategyFactory {

    private final Map<Class<? extends BaseUserUpdateDTO>, UserUpdateStrategy<? extends BaseUserUpdateDTO>> strategyMap;

    private UserUpdateStrategyFactory(List<UserUpdateStrategy<? extends BaseUserUpdateDTO>> strategies){
        strategyMap = new HashMap<>();
        for(UserUpdateStrategy<? extends BaseUserUpdateDTO> strategy : strategies){
            strategyMap.put(strategy.getDTOClass(), strategy);
        }
        System.out.println("strategyMap size:"+strategyMap.size());
        for(Class<? extends BaseUserUpdateDTO> clazz : strategyMap.keySet()){
            System.out.println(clazz.toString());
        }
    }

    public <T extends BaseUserUpdateDTO> UserUpdateStrategy<T> getStrategy(Class<T> dtoClass){
        UserUpdateStrategy<? extends BaseUserUpdateDTO> strategy = strategyMap.get(dtoClass);
        if(strategy == null)
            throw new CustomException(ResultCodeEnum.UNDEFINED_ERROR);
        return (UserUpdateStrategy<T>) strategy;
    }

}
