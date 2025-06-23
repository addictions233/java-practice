package com.one.threadpool.schedule;

import com.one.jmm.VolatileDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/05/24
 */
public class ScheduleThreadPoolExecutorTest {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        long start = System.currentTimeMillis();
        AtomicLong count = new AtomicLong(0);
        // 延迟10s执行异步任务
        executor.schedule(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("异步任务执行了");
                System.out.println((System.currentTimeMillis() - start) / 1000);
                return null;
            }
        }, 3, TimeUnit.SECONDS);


        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run()  {
                System.out.println("异步任务执行了");
                System.out.println((System.currentTimeMillis() - start) / 1000);
                if (count.get() < 10) {
                    count.incrementAndGet();
                } else {
                    System.out.println("任务执行完毕");
                    executor.shutdownNow();
                }
            }
        }, 0,3, TimeUnit.SECONDS);
    }
}
