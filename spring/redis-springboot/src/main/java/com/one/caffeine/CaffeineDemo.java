package com.one.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @description: 测试本地缓存Caffeine的使用
 * @author: wanjunjie
 * @date: 2024/10/17
 */
public class CaffeineDemo {

    public static void main(String[] args) throws InterruptedException {
        // 核心接口: Cache接口
        Cache<String, Product> cache = Caffeine.newBuilder()  // 构建一个Caffeine实例对象
                .maximumSize(500) // 最大缓存数量
                .initialCapacity(100) // 初始容量
                .expireAfterWrite(10, TimeUnit.MINUTES) // 写入后过期时间
                .expireAfterAccess(3, TimeUnit.SECONDS) // 访问后过期时间
                .build();
        // 添加缓存值
        cache.put("小米手机", new Product(3L, "小米手机", new BigDecimal(300)));
        // 访问缓存值, 如果没有获取到调用Function函数获取
        Product product = cache.get("华为手机", key -> new Product(1L, key, new BigDecimal("100")));
        System.out.println(product);
        Thread.sleep(3000);
        Product xiaomi = cache.getIfPresent("小米手机");
        System.out.println(xiaomi);

        System.out.println("------------------------------------------------------------");
        LoadingCache<String, Product> loadingCache = Caffeine.newBuilder().maximumSize(500)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .expireAfterAccess(3, TimeUnit.SECONDS) // 访问后过期时间
                .build(key -> new Product(2L, key, new BigDecimal("200"))); // 调用CacheLoader加载缓存值
        System.out.println(loadingCache.get("苹果手机"));
        Thread.sleep(3000);
        // 访问缓存值, 如果缓存值已经实现, 不会使用CacheLoader再次加载的
        Product apple = loadingCache.getIfPresent("苹果手机");
        System.out.println(apple); // 不会调用CacheLoader加载, 这里还是输出null
        // 只有调用getAll方法才会调用CacheLoader加载缓存值
        for (Map.Entry<String, Product> entry : loadingCache.getAll(Collections.singleton("苹果手机")).entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Product {

        private Long id;

        private String name;

        private BigDecimal price;
    }
}
