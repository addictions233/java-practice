package com.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 构建响应式的 http请求客户端
     * @param lb 负载均衡
     * @return WebClient
     */
    @Bean
    public WebClient webClient(ReactorLoadBalancerExchangeFilterFunction lb) {
        return WebClient.builder()
                .filter(lb)
                .build();
    }
}
