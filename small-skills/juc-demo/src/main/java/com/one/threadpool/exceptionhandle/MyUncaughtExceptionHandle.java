package com.one.threadpool.exceptionhandle;

/**
 * @author one 自定义异步线程异常处理器
 * ThreadGroup类实现自Thread的内部接口UncaughtExceptionHandler。
 * 捕捉崩溃日志公共类实现的也是Thread类的这个内部接口,
 * 所以要定义自己的异步线程异常处理器可以继承ThreadGroup类,也可以实现Thread.UncaughtExceptionHandler接口
 */
public class MyUncaughtExceptionHandle implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t.getName() + "的自定义异步线程异常处理器执行了...");
        System.out.println("caught异常: " + e);
    }
}