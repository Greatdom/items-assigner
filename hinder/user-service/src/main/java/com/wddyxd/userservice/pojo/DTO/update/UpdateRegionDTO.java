package com.wddyxd.userservice.pojo.DTO.update;


import com.wddyxd.common.paramValidateGroup.UpdateGroup;
import jakarta.validation.constraints.NotBlank;

/**
 * @program: items-assigner
 * @description: 更新地区接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 16:04
 **/

public class UpdateRegionDTO extends BaseUserUpdateDTO{

    @NotBlank(message = "区域不能为空", groups = {UpdateGroup.class})
    private String region;

    public String getRegion(){
        return region;
    }

    public void setRegion(String region){
        this.region = region;
    }

}
