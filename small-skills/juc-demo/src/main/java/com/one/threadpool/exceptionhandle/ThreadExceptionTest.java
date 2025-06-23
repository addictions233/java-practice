package com.one.threadpool.exceptionhandle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author one
 * 测试异步线程中抛出异常的处理
 */
public class ThreadExceptionTest {
    public static void main(String[] args) throws InterruptedException {
        // 利用线程工程对象创建线程对象
        Thread thread = new Thread(new MyRunnable());
        // 为单个线程设置线程的异常处理器
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandle());
        thread.start();
        // 该方法所属的线程会等该线程执行结束后再执行
        // 将多个线程的并发执行改为串行执行
        thread.join();

        // 测试不使用异步线程异常处理器对异步线程进行处理
        System.out.println("=======================");
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();
        thread2.join();

        /**
         * 不管有没有设置异步线程的异常处理器,一旦异步线程抛出异常都会终止执行
         * 设置了异步线程异常处理器出现异常时会调用异常处理器对象中的 uncaughtException()方法
         * 没有设置异步线程异常处理器的直接抛出异常
         */
        System.out.println("=======================");
        // 创建线程工程对象
        ThreadFactory myThreadFactory = new MyThreadFactory();
        // 使用线程工厂对象为线程池中的所用线程设置异常处理器
        ExecutorService exec = Executors.newCachedThreadPool(myThreadFactory);
        exec.execute(new MyRunnable());
        exec.shutdown();
    }
}
