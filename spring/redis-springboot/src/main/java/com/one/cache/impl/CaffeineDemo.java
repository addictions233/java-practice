package com.one.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @description: 测试本地缓存Caffeine的使用
 * @author: wanjunjie
 * @date: 2024/10/17
 */
public class CaffeineDemo {

    public static void main(String[] args) {
        Cache<String, Product> cache = Caffeine.newBuilder().maximumSize(500)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .initialCapacity(100)
                .build();
        Product product = cache.get("华为手机", new Function<String, Product>() {
            @Override
            public Product apply(String key) {
                return new Product(1L, key, new BigDecimal("100"));
            }
        });
        System.out.println(product);


        LoadingCache<String, Product> loadingCache = Caffeine.newBuilder().maximumSize(500)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Product>() {
                    @Override
                    public Product load(@NonNull String key) throws Exception {
                        return new Product(2L, key, new BigDecimal("200"));
                    }
                });
        System.out.println(loadingCache.get("苹果手机"));
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
