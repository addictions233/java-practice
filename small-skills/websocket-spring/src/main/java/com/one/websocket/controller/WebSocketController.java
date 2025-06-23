package com.one.websocket.controller;

import com.one.websocket.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: WebSocketController
 * @Description: 请求Controller类
 * @Author: one
 * @Date: 2021/12/15
 */
@RestController
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 客户端发送消息的入口
     * MessageMapping注解定义客户端向服务器推送消息的访问路径  用于websocket
     * SendTo注解表示服务器 return的结果作为消息推送到topic主题上,所有订阅了topic主题的客户端都能接收到这条消息
     *
     * @param ChatMessage 消息实体
     */
    @MessageMapping("/send")
    @SendTo("/topic")
    public ChatMessage say(@Payload ChatMessage ChatMessage) {
        return ChatMessage;
    }

    /**
     * GetMapping注解用于定义http请求的访问路径
     *
     * @param msg
     * @return
     */
    @GetMapping("/send")
    public String msgReply(@RequestParam String msg) {
        // 将消息内容 msg 发送到 "/topic"主题上
        messagingTemplate.convertAndSend("/topic", msg);
        return msg;
    }
}
