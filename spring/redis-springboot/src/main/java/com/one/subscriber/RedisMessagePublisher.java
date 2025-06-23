package com.one.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 消息发布者：用于发布消息到指定的通道。在 sendMessage 方法中，
 * 我们使用#convertAndSend方法将消息发送到名为 "your-channel" 的通道。
 * @author: wanjunjie
 * @date: 2024/10/10
 */
@Component
@Slf4j
public class RedisMessagePublisher {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void pushMessage(String channel, Object message) {

        // 基于模式(pattern)的发布/订阅
        // redisTemplate.convertAndSend("your-pattern-channel-1", "Hello, Redis!");

        // 基于频道(Channel)的发布/订阅
        // Channel就类似于mq中的topic
        redisTemplate.convertAndSend(channel, message);
        log.info("向channel:{}中发布消息:{}", channel, message);
    }
}
