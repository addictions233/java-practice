package com.one.globalfilter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 在网关服务中使用 过滤器 进行认证调用
 */
@Component
public class ValidateTokeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 不需要校验token的白名单
        List<String> urls = new ArrayList<>();
        urls.add("/validateToken");
        urls.add("/login");

        String path = exchange.getRequest().getPath().value();

        // 白名单直接放行
        if (urls.contains(path)) {
            // 调用过滤器链中的下一个过滤器
            return chain.filter(exchange);
        }

        // 调用auth服务验证token
        return chain.filter(exchange);
    }
}
