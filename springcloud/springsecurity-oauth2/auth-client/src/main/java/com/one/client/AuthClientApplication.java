package com.one.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: OAuth2.0客户端服务 (微服务框架下集成到网关中)
 * @author: wanjunjie
 * @date: 2024/11/29
 */
@SpringBootApplication
public class AuthClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthClientApplication.class, args);
    }
}
