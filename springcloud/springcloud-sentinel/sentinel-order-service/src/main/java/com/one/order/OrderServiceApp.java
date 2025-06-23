package com.one.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: OrderServiceApp
 * @Description: 启动类
 * @Author: one
 * @Date: 2022/04/10
 */
@SpringBootApplication
@EnableFeignClients // 开启feign接口远程调用
public class OrderServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApp.class, args);
    }
}
