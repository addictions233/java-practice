package com.one.config;

import com.one.lock.DistributedLockAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author one
 */
@Configuration
public class CacheManagerAutoConfiguration {

    /**
     * Spring Cache 需要一个 CacheManager 实例来管理缓存
     * @param redisConnectionFactory redis连接工厂对象
     * @param redisTemplate RedisTemplate
     * @return CacheManager
     */
    @Bean("redisCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisTemplate<?, ?> redisTemplate) {
        // 设置Redis缓存配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                //默认30分钟过期
                .entryTtl(Duration.ofMinutes(30))
                .computePrefixWith(cacheName -> cacheName.concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        //允许存空值，解决缓存穿透（如果对于不过期的缓存数据，自己用#result解决）
               // .disableCachingNullValues();
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                // .withInitialCacheConfigurations()
                .build();
    }
}
