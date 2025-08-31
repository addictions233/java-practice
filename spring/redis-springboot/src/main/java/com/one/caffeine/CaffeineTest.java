package com.one.caffeine;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author zjk
 */
public class CaffeineTest {

    // 模拟redis缓存
    private static HashMap<String, String> redisLike = new HashMap<>();

    static {
        redisLike.put("k1", "v1");
        redisLike.put("k2", "v2");
    }

    // 模拟数据库
    private static HashMap<String, String> dbLike = new HashMap<>();

    static {
        dbLike.put("k1", "v1");
        dbLike.put("k2", "v2");
        dbLike.put("k3", "v3");
        dbLike.put("k4", "v4");
    }

    public static String getValue(String key) {
        return localCache.get(key);
    }


    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 4, 2, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "myThread");
        }
    });

    // 注意，注意 这里为了方便模拟实际使用过程中可能遇到的情况，参数设置比较极端
    private static LoadingCache<String, String> localCache = Caffeine.newBuilder()
            .maximumSize(1) // 最大缓存容量个数
            .expireAfterWrite(50, TimeUnit.SECONDS) // 过期时间
            .refreshAfterWrite(1, TimeUnit.SECONDS) // 写入后多久进行缓存刷新
            .build(new CacheLoader<String, String>() {

                @Nullable
                @Override
                public String load(@NonNull String token) {
                    // 模拟三级缓存刷入数据
                    String value = null;
                    if (redisLike.containsKey(token)) {
                        System.out.println("从redis中获取数据");
                        value = redisLike.get(token);
                    } else if (dbLike.containsKey(token)) {
                        System.out.println("从db中获取数据");
                        value = dbLike.get(token);
                        redisLike.put(token, value); // 需要给redis缓存设置过期时间，防止内存满了
                    } else {
                        System.out.printf("获取到%s的数据为空%n", token);
                    }
                    return value;
                }

                @Override
                public @NonNull
                CompletableFuture<String> asyncReload(@NonNull String key, @NonNull String oldValue, @NonNull Executor executor) {
//        System.out.println("自动刷新缓存,key:" + key +",value:"+ oldValue);
                    return CompletableFuture.supplyAsync(() -> {
//          System.out.println("执行异步刷新");
                        return oldValue;
                    }, executorService);
                }
            });

    public static void main(String[] args) throws InterruptedException {
        System.out.println("value:" + localCache.get("k0"));
        Thread.sleep(300);
        System.out.println("value:" + localCache.get("k0"));
        dbLike.put("k0", "v0"); // 模拟中途刷新数据
        Thread.sleep(300);
        System.out.println("value:" + localCache.get("k0"));

        System.out.println("value:" + localCache.get("k1"));
        Thread.sleep(300);
        System.out.println("value:" + localCache.get("k1"));
        Thread.sleep(300);
        System.out.println("value:" + localCache.get("k1"));

        System.out.println("value:" + localCache.get("k3"));
        Thread.sleep(1000);
        System.out.println("value:" + localCache.get("k3"));
        Thread.sleep(1000);
        System.out.println("value:" + localCache.get("k3"));

        localCache.refresh("k4");// 模拟主动刷新数据
        System.out.println("主动刷新数据k4数据");
        System.out.println("value:" + localCache.get("k4"));
        localCache.put("k4", "v004");
        System.out.println("主动刷新数据k4数据");
        Thread.sleep(1000);
        System.out.println("value:" + localCache.get("k4"));
        Thread.sleep(1000);
        System.out.println("value:" + localCache.get("k4"));

        Thread.sleep(2000);// k0过期后重新获取
        System.out.println("value:" + localCache.get("k0"));
    }
}
