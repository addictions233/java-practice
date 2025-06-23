package com.one.nacos.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: RestTemplateConfig
 * @Description: 配置RestTemplate对象,用于服务之间的请求调用, Eureka,Nacos,Consul默认都引入了Ribbon的依赖
 * @Author: one
 * @Date: 2022/04/05
 */
@Configuration
public class RestTemplateConfig {
    /**
     * nacos1.x版本默认使用Ribbon作为负载均衡器,nacos注册中心中的某个服务为了保证高可用,搭建了集群,存在多个实例
     * 使用RestTemplate请求到哪台实例,可以在Ribbon中设置,
     * 由于Ribbon是作用于consumer(消费者端或者客户端),所以又叫客户端的负载均衡器, 区别于Nginx服务端的负载均衡器
     * @return org.springframework.web.client.RestTemplate
     */
    @Bean("restTemplate")
    @LoadBalanced // 使用Ribbon作为consumer客户端的负载均衡器
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
