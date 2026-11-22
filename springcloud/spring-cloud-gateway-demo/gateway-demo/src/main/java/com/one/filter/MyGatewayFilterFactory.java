package com.one.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义过滤器
 */
@Component
public class MyGatewayFilterFactory implements GatewayFilterFactory<MyGatewayFilterFactory.MyConfig> {

    @Override
    public Class<MyConfig> getConfigClass() {
        return MyConfig.class;
    }

    @Override
    public GatewayFilter apply(MyConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("configTag:" + config.getTag());
                // 使用 attributes 在过滤器中传递数据
                exchange.getAttributes().put("name", "zhangsan");
                return chain.filter(exchange);
            }
        };
    }

    @Data
    public static class MyConfig {
        private String tag;
    }
}
