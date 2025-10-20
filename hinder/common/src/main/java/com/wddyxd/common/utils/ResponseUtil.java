package com.wddyxd.common.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * @program: items-assigner
 * @description: 给Http协议的响应结果进行封装，用于非springMVC的响应结果返回
 * @author: wddyxd
 * @create: 2025-10-20 20:07
 **/

public class ResponseUtil {

    public static void out(HttpServletResponse response, Result<?> result) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
