package com.one.cache.impl;

import com.one.cache.IRemoteCache;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用Redisson远程缓存实现类
 * @author one
 */
@Component
public class RedissonRemoteMapCacheImpl implements IRemoteCache {
    @Resource
    private RedissonClient redissonClient;

    @Override
    public Object get(String cacheName, String key) {
        return redissonClient.getMapCache(cacheName).get(key);
    }

    @Override
    public Map<String, Serializable> getAll(String cacheName, Set<String> keys) {
        RMapCache<String, Serializable> mapCache = redissonClient.getMapCache(cacheName);
        return mapCache.getAll(keys);
    }

    @Override
    public void put(String cacheName, String key, Serializable value, Long expireTime, TimeUnit timeUnit) {
        RMapCache<String, Object> mapCache = redissonClient.getMapCache(cacheName);
        mapCache.put(key, value, expireTime, timeUnit);
    }

    @Override
    public void putAll(String cacheName, Map<String, Serializable> cacheData, Long expireTime, TimeUnit timeUnit) {
        RMapCache<String, Serializable> mapCache = redissonClient.getMapCache(cacheName);
        mapCache.putAll(cacheData, expireTime, timeUnit);
    }

    @Override
    public void removeAll(String cacheName) {
        redissonClient.getMapCache(cacheName).clear();
    }

    @Override
    public void remove(String cacheName, Set<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        RMapCache<String, Serializable> mapCache = redissonClient.getMapCache(cacheName);
        mapCache.fastRemove(keys.toArray(new String[0]));
    }


}
