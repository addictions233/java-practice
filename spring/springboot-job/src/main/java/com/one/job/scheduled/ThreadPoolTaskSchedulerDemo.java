package com.one.job.scheduled;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Instant;
import java.util.Date;

/**
 * Spring基于ScheduledThreadPoolExecutor 提供了一个 定时任务调度工具类 ThreadPoolTaskScheduler
 * 增加了触发器的功能, 例如 Cron表达式
 */
public class ThreadPoolTaskSchedulerDemo {

    public static void main(String[] args) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.initialize();

        // 底层还是依赖JUC提供的ScheduledThreadPoolExecutor来执行定时任务
        taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行了任务!");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 获取上一次任务执行成功的时间
                Date lastCompletion = triggerContext.lastCompletionTime();
                // 返回下一次任务执行的时间
                return lastCompletion != null ? Date.from(lastCompletion.toInstant().plusMillis(1000)) : new Date();
            }
        });
    }
}
