package com.one.stream.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * @author one
 * @description 自定义接收消息的通道
 * @date 2022-9-4
 */
public interface CustomChannelBinder {
    /**
     * 自定义接收消息的管道名称
     */
    String INPUT = "input1";

    /**
     * 接收消息的管道
     *
     * @return MessageChannel
     */
    @Input(CustomChannelBinder.INPUT)
    MessageChannel output1();
}
