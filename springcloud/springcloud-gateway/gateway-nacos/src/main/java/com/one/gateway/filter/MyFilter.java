package com.one.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName: MyFilter
 * @Description: 全局过滤器, 对所有的请求都有效,对请求进行拦截或者放行
 * @Author: one
 * @Date: 2021/02/09
 */
@Component
public class MyFilter implements GlobalFilter, Ordered {
    /**
     * 进行过滤的方法
     * @param exchange web交换机
     * @param chain 过滤器链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("过滤器执行了");
        return chain.filter(exchange);
    }

    /**
     * 执行过滤器链的顺序  返回结果可以是负数,但是最后别用负数,没必要
     * @return 返回整数从小到大排列依次是第一个过滤器,第二个过滤器,....
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
