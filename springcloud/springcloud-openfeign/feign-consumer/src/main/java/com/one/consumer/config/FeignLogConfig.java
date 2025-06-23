package com.one.consumer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: FeignLogConfig
 * @Description: 用于配置Feign调用时的日志记录,
 * feign配置有两种形式: 1, 第一种是注入配置Bean对象   2,  在属性文件application.yml中配置
 * @Author: one
 * @Date: 2021/01/18
 */
@Configuration
public class FeignLogConfig {
    /**
     * 使用该配置类会记录feign接口的详细调用信息,包括调用接口的请求方式, 请求地址和参数
     * 如果不配置,日志中不会记录feign接口的详细调用信息
     * @return feign包下的Logger.Level对象
     */
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
