package com.wddyxd.common.utils.encoder;


import java.util.random.RandomGenerator;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-06 13:05
 **/

public class EmailCodeGetter implements Encoder{

    @Override
    public String encode(String originalCode) {
        int randomCode = RandomGenerator.getDefault().nextInt(100000, 1000000);
        System.out.println("EmailCode---随机验证码为：" + randomCode);
        return String.valueOf(randomCode);
    }
}
