package com.one.threadpool.exceptionhandle;

/**
 * @ClassName: ThreadExceptionRunner
 * @Description: 线程任务对象
 * @Author: one
 * @Date: 2021/07/07
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "执行了....");
        int i = 1 / 0;
        System.out.println(Thread.currentThread().getName() + "执行结束了...");
    }
}
