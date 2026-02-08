package com.one.job.scheduled;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author one
 * 测试JUC提供的定时调度线程池 ScheduledThreadPoolExecutor
 */
public class ScheduledThreadPoolExecutorDemo {

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

        // 第一种延迟执行: 只执行一次
//        executor.schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + "执行了任务!");
//            }
//        }, 1, TimeUnit.SECONDS);
//
//        // 第二种: 按照固定频率执行
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行了任务!");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 1, 2, TimeUnit.SECONDS);

        // 第三种: 按照固定延迟执行任务
//        executor.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + "执行了任务!");
//                try {
//                    Thread.sleep(5);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, 1, 1, TimeUnit.SECONDS);
    }
}
