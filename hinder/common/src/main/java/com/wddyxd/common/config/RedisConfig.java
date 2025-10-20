package com.wddyxd.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: items-assigner
 * @description: 装配了RedisTemplate的Bean并设置了序列化方式
 * @author: wddyxd
 * @create: 2025-10-20 19:55
 **/

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 设置键（Key）的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // 设置值（Value）的序列化方式
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置 Hash 类型的键和值的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}