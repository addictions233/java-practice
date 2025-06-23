package com.one.user.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * @author Fox
 */
@Configuration
public class RestTemplateConfig {
    
    @Bean
    @LoadBalanced // restTemplate在调用时会使用负载均衡策略
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
