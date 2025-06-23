package com.one.threadpool.exceptionhandle;

import java.util.concurrent.ThreadFactory;

/**
 * @author one
 * 线程工程类: 用来创建线程对象的工厂类,即创建Thread类的对象
 */
public class MyThreadFactory implements ThreadFactory {
    /**
     * 实例工厂方法,用来创建Thread线程对象
     * @param runnable 线程任务对象
     * @return
     */
    @Override
    public Thread newThread(Runnable runnable) {
        System.out.println("create thread t");
        Thread thread = new Thread(runnable);
        System.out.println("set uncaughtException for t");
        //为线程对象设置自定义的异常处理器
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandle());
        return thread;
    }
 
}