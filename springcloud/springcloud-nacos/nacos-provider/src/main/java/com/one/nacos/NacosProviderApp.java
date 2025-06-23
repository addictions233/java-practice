package com.one.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: ConfigApplication
 * @Description: 服务端, 服务提供者
 * @Author: one
 * @Date: 2021/03/04
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApp {
    public static void main(String[] args) {

        SpringApplication.run(NacosProviderApp.class, args);
    }
}
