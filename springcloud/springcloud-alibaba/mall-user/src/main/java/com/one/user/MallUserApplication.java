package com.one.user;

import com.one.user.config.CustomLoadBalancerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@LoadBalancerClient(value = "mall-order", configuration = CustomLoadBalancerConfiguration.class)  // 自定义负载均衡策略
@EnableDiscoveryClient
@EnableFeignClients // 扫描和注册feign客户端的bean
public class MallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class, args);
    }

}
