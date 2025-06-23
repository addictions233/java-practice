package com.one.websocket.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @ClassName: WebSocketConfig
 * @Description: Spring支持STOMP协议的WebSocket通信，可以实现订阅和广播，采用STOMP格式协议
 * @Author: one
 * @Date: 2021/12/15
 */
@Configuration
@EnableWebSocketMessageBroker //开启webSocket over stomp, 这样才能在Controller中使用MessageMapping和template这些
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp服务端口
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 设置websocket的访问地址, 允许跨域访问, 支持 socketJs
        registry.addEndpoint("/websocket")
                // websocket是支持跨域访问的,如果降级为长轮询就得支持跨域访问
                .setAllowedOrigins("*")
                // 允许使用SocketJs使用, 可降级为轮询方式, 防止某些浏览器客户端不支持websocket协议的降级策略
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 只支持特定主题的消息, 这里只支持 topic 和 queue 两个主题
        registry.enableSimpleBroker("/topic/", "/queue/");
        // 客户端访问必须带 /app 前缀
        registry.setApplicationDestinationPrefixes("/app");
    }
}
