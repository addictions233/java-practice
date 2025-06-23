package com.one.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @description:  过滤器工厂（ GatewayFilter Factories）: 定义单个服务的过滤器: 针对单个服务生效, 必须在配置文件中指定该过滤器
 * GatewayFilter是网关中提供的一种过滤器，可以对进入网关的请求和微服务返回的响应做处理
 * 继承AbstractNameValueGatewayFilterFactory且我们的自定义名称必须要以GatewayFilterFactory结尾并交给spring管理。
 *
 * @author: wanjunjie
 * @date: 2024/11/29
 */
@Component
@Slf4j
public class CheckAuthGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            log.info("调用CheckAuthGatewayFilterFactory==="
                    + config.getName() + ":" + config.getValue());
            return chain.filter(exchange);
        };
    }
}
