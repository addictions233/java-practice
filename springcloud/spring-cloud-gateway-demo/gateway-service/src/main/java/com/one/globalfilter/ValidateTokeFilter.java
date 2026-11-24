package com.one.globalfilter;

import jakarta.annotation.Resource;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 在网关服务中使用 过滤器 进行认证调用
 */
@Component
public class ValidateTokeFilter implements GlobalFilter, Ordered {

    @Resource
    private WebClient webClient;

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

        ServerHttpResponse response = exchange.getResponse();

        // 调用auth服务验证token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        return webClient.get()
                .uri("lb://auth-service/validateToken")
                .header("token", token)
                .retrieve()
                .bodyToMono(Integer.class)
                .flatMap(uid -> {
                    // 验证token成功, 将uid添加到headers中, 方便后续filter或者微服务直接取出使用
                    ServerWebExchange newExchange = exchange.mutate().request(exchange.getRequest().mutate().headers(
                            headers -> {
                                headers.put("uid", List.of(String.valueOf(uid)));
                            }
                    ).build()).build();

                    // 添加到 attributes中
                    newExchange.getAttributes().put("uid", uid);

                    return chain.filter(newExchange);
                })
                .doOnError(e -> System.out.println("校验token失败:" + e.getMessage()))
                .onErrorResume(e -> {
                    // 当出现错误时，返回“校验token失败”的响应，并且不执行后续的chain逻辑
                    response.setStatusCode(HttpStatus.UNAUTHORIZED); // 设置合适的HTTP状态码
                    response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                    byte[] bytes = "校验token失败".getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                });
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
