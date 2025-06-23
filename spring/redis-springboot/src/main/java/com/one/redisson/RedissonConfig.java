package com.one.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 配置redissonClient的bean对象, redissonClient作为redis的java客户端实现, 和jedis类似
 * 使用redisson-springboot-starter默认会创建RedissonClient的Bean对象，不用手动创建
 */
@Configuration
public class RedissonConfig {

    @Value("${redisson.address}")
    private String address;

    @Value("${redisson.database}")
    private int database;

    /**
     * 构建RedissonClient的bean对象
     */
    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        // 以单机的redis, 还可以使用主从, 哨兵或者集群架构
        config.useSingleServer()
                .setAddress(address)
                .setDatabase(database);
        return Redisson.create(config);
    }

}