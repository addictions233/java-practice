package com.one.redisson;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 使用Redisson的延迟队列DelayQueue实现订单到期未支付,自动关闭
 * @author: wanjunjie
 * @date: 2025/03/26
 */
@Service
@Slf4j
public class RedissonDelayQueue {

    @Resource
    private RedissonClient redissonClient;

    public static final String ORDER_QUEUE = "orderQueue";

    /**
     * 构建延迟队列对象
     */
    public RDelayedQueue<String> delayedQueue;

    @PostConstruct
    public void initDelayQueue() {
        // RBlockingQueue：存储任务的阻塞队列，用于实际的订单处理。
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue(ORDER_QUEUE);
        // RDelayedQueue：延时队列，用于在指定时间后将任务推送到 RBlockingQueue
        delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
    }

    @PreDestroy
    public void destroyDelayQueue() {
        if (Objects.nonNull(delayedQueue)) {
            // 在该对象不再需要的情况下，应该主动销毁。仅在相关的Redisson对象也需要关闭的时候可以不用主动销毁。
            delayedQueue.destroy();
        }
    }

    public void offerUnpaidOrder(String orderId, Long delayTime, TimeUnit timeUnit) {
        if (StringUtils.isBlank(orderId) || Objects.isNull(delayTime) || Objects.isNull(timeUnit)) {
            throw new IllegalArgumentException();
        }
        // 延迟消息入队
        delayedQueue.offer(orderId, delayTime, timeUnit);
    }


    /**
     * 创建一个监听器来处理延时队列中的订单。
     * CommandLineRunner 是一个接口，用于在 Spring Boot 应用程序启动后立即执行一些特定的代码逻辑。
     */
    @Component
    @DependsOn("redissonDelayQueue")
    public static class OrderQueueListener implements CommandLineRunner {

        @Resource
        private RedissonClient redissonClient;

        /**
         * Callback used to run the bean.
         *
         * @param args incoming main method arguments
         * @throws Exception on error
         */
        @Override
        public void run(String... args) throws Exception {
            CompletableFuture.runAsync(() ->  {
                // RBlockingQueue：存储任务的阻塞队列，用于实际的订单处理。
                RBlockingQueue<Object> blockingQueue = redissonClient.getBlockingQueue(ORDER_QUEUE);
                while (true) {
                    try {
                        // 获取阻塞队列中的消息, 如果队列为空, 会阻塞
                        String orderId = blockingQueue.take().toString();
                        // 实现关闭订单的逻辑
                        System.out.println("监听到订单号:" + orderId);
                    } catch (Throwable e) {
                        log.error("消费消息异常", e);
                    }
                }
            }, Executors.newFixedThreadPool(1));
        }
    }
}
