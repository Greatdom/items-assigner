package com.wddyxd.security.pojo;


/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-11-01 16:14
 **/

public class TokenInfo {

    private Long Id;
    //用户Id、时间戳、客户端、IP、设备
    private Long timestamp;

    private String localIP;

    private String publicIP;

    private String client;

    private String device;


    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}
