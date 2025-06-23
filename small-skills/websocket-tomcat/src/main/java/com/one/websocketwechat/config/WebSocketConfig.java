package com.one.websocketwechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName: WebSocketConfig
 * @Description: 使用SpringBoot内嵌的tomcat容器提供的websocket并不能自动加载@ServerEndpoint注解声明的对象,
 * 应该是无法使用tomcat提供的SPI机制
 * @Author: one
 * @Date: 2021/05/16
 */
@Configuration
public class WebSocketConfig {
    /**
     * 注入ServerEndpointExporter的bean对象作用: 自动在IOC容器中注册了使用@ServerEndpoint注解声明的类的Bean对象
     *
     * 本案例是使用SpringBoot内嵌的tomcat容器启动websocket,就需要写本配置类,
     * 同时也要引入spring-boot-starter-websocket的依赖
     * 如果使用独立的servlet容器(例如:使用外部tomcat容器启动websocket)，而不是直接使用springboot的内置容器，
     * 就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
