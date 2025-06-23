package com.one.test;

import com.one.cache.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedissonTest
 * @Description: 测试Redisson分布式锁
 * @Author: one
 * @Date: 2022/03/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTest {
    @Autowired
    private RedissonClient redissonClient;


    private static final String productId = "001";

    @Test
    public void lockTest() {
        RLock lock = redissonClient.getLock("lock:" + productId);
        try {
            // 尝试进行加锁, 最多尝试5秒, 锁的有效期为10秒,10秒之后自动解锁
            boolean flag = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (flag) { // 加锁成功
                // 进行业务处理
                List<User> users = this.findAll();
                users.forEach(System.out::println);
            } else { // 获取锁失败
                System.out.println("获取锁失败");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 锁没有被释放,且锁被当前线程持有
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock(); // 释放锁
            }
        }
    }

    private List<User> findAll() {
        return new ArrayList<>();
    }
}
