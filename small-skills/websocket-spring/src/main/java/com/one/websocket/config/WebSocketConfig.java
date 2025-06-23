package com.one.websocket.config;

import com.one.websocket.handler.ChatMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * 针对Socket协议，Spring-Socket也帮你实现了一整套的安全规范，可以设置拦截，是否允许非指定的域名访问，等一系列效果；
 * （建议深度使用Spring的项目可以引入Spring-Socket做一整套的控制，因为Spring Socket的确帮你实现了很多一整套的安全认证的功能，
 *
 * @author one
 */
@Configuration
@EnableWebSocket  // 开启spring对websocket的支持
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 配置webSocketHandler
     *
     * @param registry webSocketHandler注册器
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 此处与客户端的 URL 相对应
        registry.addHandler(webSocketHandler(), "/send");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ChatMessageHandler(template);
    }

}

