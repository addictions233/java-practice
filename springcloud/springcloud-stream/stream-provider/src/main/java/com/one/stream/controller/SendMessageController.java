package com.one.stream.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.one.stream.config.CustomChannelBinder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: SendMessageController
 * @Description: TODO
 * @Author: one
 * @Date: 2022/06/13
 */
@RestController
@RequestMapping("/send")
public class SendMessageController {
    /**
     * 注入MessageChannel的bean对象
     *   MessageChannel已经在ConsumerInfoChannel中进行了配置,bean对象的名称即是通道名称 output1
     */
    @Resource(name = CustomChannelBinder.OUTPUT)
    private MessageChannel output1;

    @GetMapping("/message")
    public void sendMessage() {
        String msg = "这是一条生产者发送的测试消息";
        // spring cloud stream是整合所有的mq,所以提供了适配所有mq的api, 要使用springframework包下的类
        Message<String> message = MessageBuilder.withPayload(msg).build();
        boolean result = output1.send(message);
        System.out.println("stream发送消息的结果:" + result);
    }
}
