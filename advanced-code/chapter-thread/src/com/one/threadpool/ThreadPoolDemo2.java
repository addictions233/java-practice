package com.one.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: ThreadPoolDemo2
 * @Description: newFixedThreadPool 创建指定线程个数的线程池
 * @Author: one
 * @Date: 2021/07/08
 */
public class ThreadPoolDemo2 {
    public static void main(String[] args) {
        // 创建固定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // ThreadPoolExecutor类实现了ExecutorService接口,ThreadPoolExecutor类是真正创建线程池的类
        ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) executorService;
        // 获取线程池中的线程个数: 起始线程个数为0
        System.out.println(poolExecutor.getPoolSize());

        // 线程池调用者对象提交线程任务:可以从控制台的打印结果看到最多有3个线程执行线程任务
        executorService.execute( () -> System.out.println(Thread.currentThread().getName() + "执行了...."));
        executorService.execute( () -> System.out.println(Thread.currentThread().getName() + "执行了...."));
        executorService.execute( () -> System.out.println(Thread.currentThread().getName() + "执行了...."));
        executorService.execute( () -> System.out.println(Thread.currentThread().getName() + "执行了...."));
        executorService.execute( () -> System.out.println(Thread.currentThread().getName() + "执行了...."));

        // 由于最大线程个数为3,所以总量为3
        System.out.println(poolExecutor.getPoolSize());

        // 关闭线程池
        poolExecutor.shutdown();
    }
}
