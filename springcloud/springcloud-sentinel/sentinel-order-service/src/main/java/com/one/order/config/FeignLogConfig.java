package com.one.order.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: FeignLogConfig
 * @Description: 配置feign的日志记录, 编写配置类
 * @Author: one
 * @Date: 2022/04/10
 */
@Configuration  // 如果注册为Bean对象, 则全局生效
public class FeignLogConfig {
    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
