package com.one.caffeine;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.cache.Cache;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 测试Caffeine异步加载缓存
 * 异步加载缓存值
 * @author one
 */
public class CaffeineAsyncLoadTest {

    public static void main(String[] args) throws Exception {
        // AsyncCache是Cache的同级接口, 这个接口是来实现数据的异步加载处理操作的
        // 而AsyncLoading是AsyncCache的子接口
        AsyncLoadingCache<String, String> asyncCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .buildAsync((key, executor) -> CompletableFuture.supplyAsync(() -> "value"));
        // 往缓存中异步添加数据
        asyncCache.put("name", CompletableFuture.supplyAsync(() -> "张三"));
        // 获取缓存中的数据
        System.out.println(asyncCache.getIfPresent("name").get(10, TimeUnit.SECONDS));
        asyncCache.getAll(Collections.singleton("name")).thenAccept((map) -> {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
            }
        });
    }
}
