package com.one.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ThreadPoolDemo3
 * @Description: 真正创建线程池对象的类 ThreadPoolExecutor, 以及创建线程池的七个参数
 *              任务的拒绝策略:
 *                  ThreadPoolExecutor.AbortPolicy  丢弃任务并抛出RejectedExecutionException异常 是默认的拒绝策略
 *                  ThreadPoolExecutor.DiscardPolicy  丢弃任务,但是不抛出异常, 这是不推荐的做法
 *                  ThreadPoolExecutor.DiscardOldstPolicy  抛弃队列中等待最久的任务,并把新任务加入到任务队列中
 *                  ThreadPoolExecutor.CallerRunsPolicy 调用任务的run()方法绕过线程池,使用主线程直接执行
 * @Author: one
 * @Date: 2021/07/08
 */
public class ThreadPoolDemo3 {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                // 核心线程的数量
                3,
                // 最大线程的数量
                5,
                // 非核心线程的空闲存活时间
                2,
                // 时间单位 枚举类 TimeUnit
                TimeUnit.SECONDS,
                // 任务的阻塞队列 BlockingQueue
                new ArrayBlockingQueue<>(5),
                // 线程工程, 按照默认的方式创建线程对象  new Thread()
                Executors.defaultThreadFactory(),
                // 任务的拒绝策略 AbortPolicy类是ThreadPoolExecutor类的静态内部类
                new ThreadPoolExecutor.AbortPolicy());
        poolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "执行了..."));
        poolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "执行了..."));
        poolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "执行了..."));
        poolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + "执行了..."));

        // 关闭线程池
        poolExecutor.shutdown();
    }
}
