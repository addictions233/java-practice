package com.one.test;

import com.one.cache.ILocalCache;
import com.one.cache.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;

/**
 * @description: 实体类
 * @author: wanjunjie
 * @date: 2024/04/24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CaffeineTest {

    @Resource
    private ILocalCache localCache;

    private final String cacheName = "cache:user";

    @Test
    public void testLocalCache() {
        User user = new User(1,"张三", 23, new Date());
        localCache.put(cacheName, user.getName(), user, null, null);
        User cacheUser = (User) localCache.get(cacheName, user.getName());
        System.out.println(cacheUser);

        localCache.remove(cacheName, Collections.singleton(user.getName()));

        Object result = localCache.get(cacheName, user.getName());
        System.out.println(result);
    }
}
