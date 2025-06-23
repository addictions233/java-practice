package com.one.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

/**
 * @description: 对应redis的key过期的事件订阅
 * @author: wanjunjie
 * @date: 2024/10/10
 */
@Service
@Slf4j
public class KeyExpireSubscriber extends KeyExpirationEventMessageListener {

    @Autowired
    public KeyExpireSubscriber(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expireKey = new String(message.getBody());
        log.info("监听到redis中过期key:" + expireKey);
    }
}
