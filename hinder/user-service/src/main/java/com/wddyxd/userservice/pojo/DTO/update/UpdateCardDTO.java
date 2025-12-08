package com.wddyxd.userservice.pojo.DTO.update;


/**
 * @program: items-assigner
 * @description: 实名认证接口的请求体
 * @author: wddyxd
 * @create: 2025-12-01 16:07
 **/

public class UpdateCardDTO extends BaseUserUpdateDTO{

    private String realName;

    private String idCard;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
