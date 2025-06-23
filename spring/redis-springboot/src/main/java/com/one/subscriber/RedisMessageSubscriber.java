package com.one.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @description: 消息监听器：实现了 MessageListener 接口，它监听指定通道上的消息
 * @author: wanjunjie
 * @date: 2024/10/10
 */
@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {
    /**
     * Callback for processing received objects through Redis.
     *
     * @param message message must not be {@literal null}.
     * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        log.info("订阅者接收到消息,channel:{}, message:{}", channel, body);
    }
}
