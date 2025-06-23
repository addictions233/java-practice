package com.one.threadpool.exceptionhandle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试线程池异常(识别线程池使用时的一些坑)
 * 1、使用submit提交子任务，一定要获取返回值Future，通过get方法获取可能出现的异常，并且可以进行捕获(推荐)
 * 2、使用execute执行子任务，异常可以被抛出，但是主线程不能捕获子任务线程中的异常
 * 3、使用submit提交子任务，只是提交，不获取返回值future，异常会被封装在子线程内部，不会抛出，主线程也无法捕获
 */
public class AsyncExceptionTest {

    public static void main(String[] args) {
        int size = 2;
        /**
         * 线程池不推荐使用Executors去创建，而是通过ThreadPoolExecutor的方式，
         * 这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 
         * 说明：Executors各个方法的弊端：
         * 1）newFixedThreadPool和newSingleThreadExecutor:主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
         * 2）newCachedThreadPool和newScheduledThreadPool:主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
         */
        // 此处没有使用ThreadPoolExecutor的后两个参数，则使用的是默认线程工厂和默认的拒绝策略(AbortPolicy--抛出RejectedExecutionException)
        ExecutorService executor = new ThreadPoolExecutor(0, size, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(size));
        try {
            for (int i = 0; i < size; i++) {
                try {
                    final int cur = i;
                    // Future获取子线程异常，外层能捕获future包装后的异常
                    Future<?> future = executor.submit(new Runnable()
                            // execute执行中的异常能抛出,但是不能被外层中的try...catch...捕获
                            // executor.execute(new Runnable()
                            // 只用submit，不获取submit的返回值-future，有异常也不会抛出
                            // executor.submit(new Runnable()
                    {
                        @Override
                        public void run() {
                            Thread.currentThread().setName("Thread-" + cur);

                            System.out.println("Current value: " + cur);
                            System.out.println(Thread.currentThread().getName());
                            if (cur < 1) {
                                return;
                            } else {
                                Integer.parseInt("abc");
                            }
                        }
                    });
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Task exception.");
                }
            }
        } finally {
            executor.shutdown();
        }

        /**
         * 1、使用线程池提交任务时，使用Future框架，可轻松获取执行结果，并在外层捕获异常
         * 2、构建线程池时，使用ThreadPoolExecutor，尽量指定阻塞队列的大小
         * 3、使用thread时，可使用Thread.setUncaughtExceptionHandler(xxx)处理线程异常
         */
    }

}