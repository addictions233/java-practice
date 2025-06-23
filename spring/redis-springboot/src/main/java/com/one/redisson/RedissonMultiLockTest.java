package com.one.redisson;

import ch.qos.logback.core.util.COWArrayList;
import io.netty.util.concurrent.NonStickyEventExecutorGroup;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description: 基于Redis的Redisson分布式联锁RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，
 * 每个RLock对象实例可以来自于不同的Redisson实例。
 * @author: wanjunjie
 * @date: 2025/03/26
 */
@Service
public class RedissonMultiLockTest {

    @Resource
    private RedissonClient redissonClient;

    public RLock getMultiLock(Collection<String> lockKeys) {
        if (CollectionUtils.isEmpty(lockKeys)) {
            throw new IllegalArgumentException();
        }
        RLock[] lockArr = lockKeys.stream().map((key) -> redissonClient.getLock(key)).toArray(RLock[]::new);
        return new RedissonMultiLock(lockArr);
    }

    public void testMultiLock(Collection<String> lockKeys) {
        RLock multiLock = this.getMultiLock(lockKeys);
        boolean lock = false;
        try {
            lock = multiLock.tryLock(5,5, TimeUnit.MINUTES);
            if (lock) {
                // 执行加锁之后的业务逻辑

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
           if (lock) {
               multiLock.unlock();
           }
        }
    }
}
