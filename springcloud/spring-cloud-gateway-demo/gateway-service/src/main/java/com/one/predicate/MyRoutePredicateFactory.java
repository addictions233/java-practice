package com.one.predicate;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * 自定义断言匹配器
 */
@Component
public class MyRoutePredicateFactory implements RoutePredicateFactory<MyRoutePredicateFactory.MyPredicateConfig> {


    @Override
    public Predicate<ServerWebExchange> apply(MyPredicateConfig config) {
        return null;
    }

    @Data
    public static class MyPredicateConfig {
        private String prefix;
    }
}
