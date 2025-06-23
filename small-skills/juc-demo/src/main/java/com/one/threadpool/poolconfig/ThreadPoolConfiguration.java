package com.one.threadpool.poolconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: ThreadPoolConfiguration
 * @Description: 在springboot工程中创建线程池ThreadPoolTaskExecutor: 核心逻辑必须在接口中同步执行，而非核心逻辑可以多线程异步执
 * @Author: one
 * @Date: 2021/05/07
 */
@Configuration
public class ThreadPoolConfiguration {
    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;
    @Value("${async.executor.thread.max_pool_size}")
    private int maxPoolSize;
    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;
    @Value("${async.executor.thread.name.prefix}")
    private String namePrefix;
    @Value("${async.executor.thread.keep_alive_seconds}")
    private int keepAliveSeconds;

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        // 设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 设置任务队列长度
        executor.setQueueCapacity(queueCapacity);
        // 设置空闲线程的存活时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 设置线程名称前缀
        executor.setThreadNamePrefix("123");
        // 设置拒绝策略, 当任务队列中的任务数量满了之后调用主线程中执行任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}
