package com.one.consumer;

import com.one.consumer.loadbalancer.CustomRandomLoadBalancerClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: FeignConsumerApp
 * @Description: 使用Feign接口远程调用
 * @Author: one
 * @Date: 2021/01/18
 */
@SpringBootApplication
@EnableFeignClients // 开启Feign接口的功能
@LoadBalancerClients(defaultConfiguration = {CustomRandomLoadBalancerClient.class})  // 指定全局的负载均衡器
//@LoadBalancerClient(name = "feign-provider",configuration = CustomRandomLoadBalancerClient.class) // 指定具体服务用某个负载均衡
//@EnableDiscoveryClient
public class FeignConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApp.class,args);
    }
}
