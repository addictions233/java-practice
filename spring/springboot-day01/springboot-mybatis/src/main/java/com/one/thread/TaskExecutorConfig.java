package com.one.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: taskExecutor
 * @Description: 线程池配置类
 * @Author: one
 * @Date: 2022/01/11
 */
@Configuration
public class TaskExecutorConfig {

    @Bean("threadPoolExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        taskExecutor.setCorePoolSize(10);
        // 设置最大线程数
        taskExecutor.setMaxPoolSize(100);
        // 设置任务队列长度
        taskExecutor.setQueueCapacity(1000);
        // 设置空闲线程存活时间
        taskExecutor.setKeepAliveSeconds(3);
        // 设置异步线程前缀
        taskExecutor.setThreadNamePrefix("async thread");
        // 设置拒绝策略, 当任务队列满了，调用者所在的线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
