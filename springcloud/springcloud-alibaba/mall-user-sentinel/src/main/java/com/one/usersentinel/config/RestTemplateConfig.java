package com.one.usersentinel.config;


import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.one.usersentinel.sentinel.ExceptionUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * @author Fox
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 将sentinel整合到restTemplate上, 使用ExceptionUtil进行统一的异常管理
     */
    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
            blockHandler = "handleBlockException",blockHandlerClass = ExceptionUtil.class,
            fallback = "handleFallback",fallbackClass = ExceptionUtil.class
    )
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
