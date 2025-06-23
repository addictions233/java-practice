package com.one.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author one
 * Executors类 创建新线程池的对象
 * ExecutorService类 执行线程任务的对象
 * static ExecutorService	newCachedThreadPool() 创建的线程池的最大线程数量可以达到int的最大值
 * Creates a thread pool that creates new threads as needed,
 * but will reuse previously constructed threads when they are available
 */

public class ThreadPoolDemo1 {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池对象
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 线程池调用对象提交线程任务
        executorService.submit(()->{
            System.out.println(Thread.currentThread().getName()+"线程正在执行");
        });

        /**
         * 让主线程休眠1秒钟,这样刚刚线程池中开辟的线程就执行完了上一个任务,当
         * 第二任务提交给线程池时,上一个线程已经是空闲状态,可以接收新的任务了
         */
//        Thread.currentThread().sleep(1000);

        executorService.submit(()->{
            System.out.println(Thread.currentThread().getName()+"线程正在执行");
        });

        //关闭线程池, 线程对象不需要关闭,当线程执行结束后自动销毁,但是线程池需要手动关闭
        executorService.shutdown();
    }
}
