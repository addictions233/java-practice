package com.one.lock;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁配置类
 *
 * @author one
 */
@Configuration(proxyBeanMethods = false)
public class DistributedLockAutoConfiguration {

    @Bean
    public DistributedLockAspect distributedLockAspect(ExpressionParser expressionParser, RedissonClient redissonClient) {
        return new DistributedLockAspect(expressionParser, redissonClient);
    }

}
