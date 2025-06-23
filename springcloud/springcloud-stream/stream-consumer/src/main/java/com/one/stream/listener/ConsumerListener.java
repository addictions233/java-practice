package com.one.stream.listener;

import com.one.stream.config.CustomChannelBinder;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author one
 * @description 消费者注册监听器
 * @date 2022-9-4
 */
@Component
public class ConsumerListener {
    /**
     * 使用@StreamListener注册监听器, value值给定通道名称, @StreamListener注解类似于@RocketMQMessageListener
     * 当消息推送给消费者时,就会调用getMessage()方法
     *
     * @param message 接收到的消息
     */
    @StreamListener(CustomChannelBinder.INPUT)
    public void getMessage(String message) {
        System.out.println("消费者获取的消息:" + message);
    }
}
