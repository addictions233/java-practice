package com.one.lock;

import com.one.expression.ExpressionParser;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class DistributedLockAutoConfiguration {

    @Bean
    public DistributedLockAspect distributedLockAspect(ExpressionParser expressionParser, RedissonClient redissonClient) {
        return new DistributedLockAspect(expressionParser, redissonClient);
    }
}
