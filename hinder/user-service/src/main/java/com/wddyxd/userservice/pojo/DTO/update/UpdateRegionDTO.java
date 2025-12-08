package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: 更新地区接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 16:04
 **/

public class UpdateRegionDTO extends BaseUserUpdateDTO{

    private String region;

    public String getRegion(){
        return region;
    }

    public void setRegion(String region){
        this.region = region;
    }

}
