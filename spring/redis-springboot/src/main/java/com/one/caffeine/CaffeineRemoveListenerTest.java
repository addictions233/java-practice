package com.one.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 测试Caffeine缓存移除监听器
 */
public class CaffeineRemoveListenerTest {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .removalListener((key, value, removeCause) -> {
                    System.out.println("key: " + key + ", value: " + value + ", cause: " + removeCause);
                })
                .evictionListener((key, value, evictionCause) -> {
                    System.out.println("key: " + key + ", value: " + value + ", cause: " + evictionCause);
                })
                .build();
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        // 手动驱逐缓存key
        cache.invalidate("k1");
        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.getIfPresent("k2"));
        System.out.println(cache.getIfPresent("k3"));
        TimeUnit.SECONDS.sleep(5);
    }
}
