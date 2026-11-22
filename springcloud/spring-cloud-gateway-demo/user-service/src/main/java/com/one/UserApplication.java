package com.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.io.IOException;

import static org.springframework.web.servlet.function.RouterFunctions.route;

/**
 * 大都督周瑜
 * 微信ID: dadudu6789
 * 专注帮助程序员提升技术实力，升职涨薪，面试跳槽
 */
@SpringBootApplication
public class UserApplication {

    @Bean
    public RouterFunction<ServerResponse> user() {
        return route()
                .GET("/test", request -> {
                    System.out.println("user...");
//                    throw new IOException();
                    return ServerResponse.status(HttpStatus.OK).body("get user success!");
                })
                .POST("/user", request -> {
                    return ServerResponse.status(HttpStatus.OK).body("get user success!");
                })
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
