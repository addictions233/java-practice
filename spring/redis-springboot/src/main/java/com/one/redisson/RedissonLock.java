package com.one.redisson;

import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author one
 * @description 测试分布式锁的使用: tuling demo
 * @date 2024-10-4
 */
@RestController
public class RedissonLock {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 模拟减库存的操作
     */
    @RequestMapping("/deductStock")
    public void deductStock() {
        final String lockKey = "lock:product_101";
        String clientId = UUID.randomUUID().toString();
        // 持有redis锁, 超时时间是30s
        // 问题: 如果业务代码执行时间超过锁的持有时间, 锁会失效
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 30, TimeUnit.SECONDS);
        if (!lock) {
            return;
        }
        try {
            // 减库存
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", stock.toString());
                System.out.println("扣减库存成功, 剩余库存数:" + stock);
            } else {
                System.out.println("扣减库存失败, 库存已不足");
            }
        } finally {
            // 释放锁
            // 问题: 先判断是否是自己持有的锁后删除锁存并发安全问题
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        }
    }


    /**
     * 使用redisson提供的分布式锁
     */
    @RequestMapping("/deductStock2")
    public void deductStock2() {
        final String lockKey = "lock:product_101";

        RLock lock = redissonClient.getLock(lockKey);
        try {
            // 持有redis锁, 超时时间是30s
            lock.lock();
//            try {
//                boolean getLock = lock.tryLock(-1, 5, TimeUnit.MINUTES);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            // 减库存
            Integer stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", stock.toString());
                System.out.println("扣减库存成功, 剩余库存数:" + stock);
            } else {
                System.out.println("扣减库存失败, 库存已不足");
            }
        } finally {
            // 释放锁
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 基于redisson实现的红锁redLock
     */
    @RequestMapping("/redLock")
    public String redLock() {
        String lockKey = "product_001";
        //这里需要自己实例化不同redis实例的redisson客户端连接，这里只是伪代码用一个redisson客户端简化了
        RLock lock1 = redissonClient.getLock(lockKey);
        RLock lock2 = redissonClient.getLock(lockKey);
        RLock lock3 = redissonClient.getLock(lockKey);

        /**
         * 根据多个 RLock 对象构建 RedissonRedLock （最核心的差别就在这里）
         */
        RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
        try {
            /**
             * waitTimeout 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
             * leaseTime   锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完）
             */
            boolean res = redLock.tryLock(10, 30, TimeUnit.SECONDS);
            if (res) {
                //成功获得锁，在这里处理业务
            }
        } catch (Exception e) {
            throw new RuntimeException("lock fail");
        } finally {
            //无论如何, 最后都要解锁
            redLock.unlock();
        }

        return "end";
    }
}
