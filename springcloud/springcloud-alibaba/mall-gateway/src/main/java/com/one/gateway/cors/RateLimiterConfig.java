package com.one.gateway.cors;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

/**
 * @author one
 * @description 使用Redis+lua脚本实现令牌桶限流的配置
 * @date 2024-12-1
 */
public class RateLimiterConfig {

    @Bean
    public KeyResolver keyResolver() {
        //url限流: 对指定的url进行限流
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
        //参数限流: 请求中有user属性才会被流控
        //return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }
}
