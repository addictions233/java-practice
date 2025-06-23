package com.one.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @ClassName: RedisBeanLoad
 * @Description: 重新设置RedisTemplate的Bean对象,如果自己不设置redisTemplate的bean对象,
 *              SpringBoot会帮我们生成RedisTemplate的Bean对象并设置序列化方式
 *      1, StringRedisTemplate默认采用的序列化器是String类型的, key和value都是String类型的序列化器
 *      2, RedisTemplate默认采用的序列化器是JDK类型的,key和value都是JDK类型的序列化器
 * @Author: one
 * @Date: 2022/03/20
 */
@Configuration
public class RedisTemplateConfig {

    /**
     * SpringBoot自动创建的Bean对象， key和value都是String类型的序列化器
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 我们自定义RedisTemplate的Bean, key的序列化用String, value的序列化用Json
     * @param factory
     * @param objectMapper
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 将key的序列化方式设置为StringRedisSerializer, key都写成String
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的field采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 将value的序列化方式设置为jackson2JsonRedisSerializer, value都写成json
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // value采用json的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value也采用json的序列化方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
