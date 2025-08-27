package com.one.springcache;

import org.springframework.beans.BeansException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 缓存工具类
 */
public class SpringCacheUtil implements ApplicationContextAware {

    public static CacheManager cacheManager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cacheManager = applicationContext.getBean(CacheManager.class);
    }

    public static Cache getCache(String cacheName) {
        // 直接使用CacheManager获取缓存
        return cacheManager.getCache(cacheName);
    }
}
