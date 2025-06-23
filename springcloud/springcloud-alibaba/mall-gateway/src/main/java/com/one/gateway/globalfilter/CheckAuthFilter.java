package com.one.gateway.globalfilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author one
 * @description 全局过滤器的作用也是处理一切进入网关的请求和微服务响应，与GatewayFilter的作用一样。
 * - GatewayFilter：网关过滤器，需要通过spring.cloud.routes.filters配置在具体的路由下，只作用在当前特定路由上，
 *                  也可以通过配置spring.cloud.default-filters让它作用于全局路由上。
 * - GlobalFilter：全局过滤器，不需要再配置文件中配置，作用在所有的路由上，最终通过GatewayFilterAdapter包装成GatewayFilterChain能够识别的过滤器。
 * @date 2024-12-1
 */
@Component
@Slf4j
public class CheckAuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取token
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (null == token) {
            log.info("token is null");
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type",
                    "application/json;charset=UTF-8");
            // 401 用户没有访问权限
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            byte[] bytes = HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes();
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            // 请求结束，不继续向下请求
            return response.writeWith(Mono.just(buffer));
        }
        //TODO 校验token进行身份认证
        log.info("校验token");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
