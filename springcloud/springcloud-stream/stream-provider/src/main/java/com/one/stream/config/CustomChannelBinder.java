package com.one.stream.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @InterfaceName: ConsumerInfoChannel
 * @Description: 自定义发送消息的通道
 * @Author: one
 * @Date: 2022/06/12
 */
public interface CustomChannelBinder {
    /**
     * 定义通道名称
     */
    String OUTPUT = "output1";

    /**
     * 定义发送消息的管道
     * 使用@Output注解如果不设置value属性值,则定义的管道名称是方法名称,如果指定了value值,则管道名称是value值名称
     * @return MessageChannel
     */
    @Output(value = CustomChannelBinder.OUTPUT)
    MessageChannel output();
}
