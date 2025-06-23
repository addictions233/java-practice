package com.one.threadpool.pooldemo;

import java.util.concurrent.*;

/**
 * author: one
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池: 核心线程数,最大线程数,空闲线程的存活时间,阻塞队列
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 500, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), new ThreadPoolExecutor.CallerRunsPolicy());
        // 调用线程执行任务
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                throw new NullPointerException();
            }
        });
        // 关闭线程池
        executor.shutdown();  // SHUT
//        executor.shutdownNow();


//        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 200, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200),
//                new ThreadFactory() {
//                    @Override
//                    public Thread newThread(Runnable r) {
//                        Thread thread = new Thread(r);
//                        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//                            @Override
//                            public void uncaughtException(Thread t, Throwable e) {
//                                System.out.println("出错拉...");
//                            }
//                        });
//                        return thread;
//                    }
//                });
//
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("...");
//                throw new NullPointerException();
//            }
//        });

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("...");
//            }
//        }).start();


//        ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Runnable task;
//                while ((task = blockingQueue.poll()) != null) {
//                    task.run();
//                }
//            }
//        }).start();


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        thread.start();
//
//        thread.interrupt();
    }
}
