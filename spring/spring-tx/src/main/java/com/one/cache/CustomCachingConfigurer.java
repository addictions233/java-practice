package com.one.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomCachingConfigurer extends CachingConfigurerSupport {
    @Override
    public CacheManager cacheManager() {
        return super.cacheManager();
    }

    @Override
    public CacheResolver cacheResolver() {
        return super.cacheResolver();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return super.keyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }
}
