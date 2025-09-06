package com.one.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 测试Caffeine缓存淘汰策略
 * 1. 基于容量的淘汰策略
 * 2. 基于时间的淘汰策略
 * 3. 基于权重的淘汰策略
 * 4. 基于自定义策略的淘汰策略
 */
public class CaffeineCacheEvictionTest {

    public static void main2(String[] args) throws InterruptedException {
        // 基于最大容量的淘汰策略
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1) // 设置缓存中保存的最大数据量
                .build();
        // 设置缓存项
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        Thread.sleep(1000);
        // 获取缓存值
        System.out.println("k1:"  + cache.getIfPresent("k1"));
        System.out.println("k2:"  + cache.getIfPresent("k2"));
    }

    public static void main3(String[] args) throws InterruptedException {
        // 基于最大权重的淘汰策略
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumWeight(100) // 设置缓存中的最大权重
                .weigher(((key, value) -> {
                    return 51;  // 实际开发中会有权重计算处理, 这里给所有的key一个死的权重值
                }))
                .build();
        // 设置缓存项
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        Thread.sleep(1000);
        // 获取缓存值
        System.out.println("k1:"  + cache.getIfPresent("k1"));
        System.out.println("k2:"  + cache.getIfPresent("k2"));
    }

    public static void main(String[] args) throws InterruptedException {
        // 基于最近访问时间的淘汰策略
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100) // 设置缓存中的最大权重
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();
        // 设置缓存项
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        Thread.sleep(1000);
        // 获取缓存值
        System.out.println("k1:"  + cache.getIfPresent("k1"));
        System.out.println("k2:"  + cache.getIfPresent("k2"));
    }
}
