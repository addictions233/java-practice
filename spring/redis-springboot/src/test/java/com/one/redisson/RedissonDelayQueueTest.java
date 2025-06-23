package com.one.redisson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedissonDelayQueueTest {

    @Resource
    private RedissonDelayQueue redissonDelayQueue;



    @Test
    public void offerUnpaidOrder() throws IOException {
        redissonDelayQueue.offerUnpaidOrder("order_001", 5L, TimeUnit.SECONDS);
        redissonDelayQueue.offerUnpaidOrder("order_002", 10L, TimeUnit.SECONDS);
        redissonDelayQueue.offerUnpaidOrder("order_003", 15L, TimeUnit.SECONDS);
        System.in.read();
    }
}