package com.one.nacos.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MyRule
 * @Description: Ribbon的负载均衡策略, 使用编码的方式指定负载均衡策略
 * @Author: one
 * @Date: 2021/01/18
 */
@Configuration
public class MyRule {

    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
