package com.one.sentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author one
 * @description 使用sentinel对切面编程的支持
 * @date 2024-11-23
 */
@Configuration
public class SentinelAspectConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        // 使用切面定义sentinel中的资源
        return new SentinelResourceAspect();
    }
}
