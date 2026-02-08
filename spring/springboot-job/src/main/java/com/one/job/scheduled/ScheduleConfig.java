package com.one.job.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.Task;

@Configuration
public class ScheduleConfig {


    /**
     * Scheduling提供的扩列配置类
     * @return 需要将扩展配置类注册为Bean对象
     */
    @Bean
    public SchedulingConfigurer schedulingConfigurer() {
        return new SchedulingConfigurer() {
            @Override
            public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
                taskRegistrar.setTaskScheduler(taskScheduler());
                // taskRegistrar.setScheduler(taskExecutor()); // 使用自定义的线程池来执行定时任务
            }
        };
    }

    /**
     * Spring默认使用的单线程池来执行所有的定时任务。
     * 如果需要并发执行多个任务，或者需要更精细地控制线程的使用，可以通过配置TaskScheduler来实现。
     * ThreadPoolTaskScheduler‌：专注于任务调度，支持定时任务或周期性任务执行（如每天、每小时执行一次），
     * 基于ScheduledThreadPoolExecutor实现，可通过Cron表达式或固定间隔调度任务
     * @return ThreadPoolTaskScheduler
     */
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("scheduled-task-");
        scheduler.initialize();
        return scheduler;
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setThreadNamePrefix("executor-task-");
        return executor;
    }
}
