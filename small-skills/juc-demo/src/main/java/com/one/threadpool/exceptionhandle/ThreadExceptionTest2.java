package com.one.threadpool.exceptionhandle;

/**
 * @author one
 *  ThreadGroup实现自Thread的内部接口UncaughtExceptionHandler。
 *  捕捉崩溃日志公共类实现的也是Thread的这个内部接口
 */
public class ThreadExceptionTest2 extends ThreadGroup{

    private ThreadExceptionTest2(){
        super("ThreadTest");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        /**
         * 当线程抛出运行时异常时,系统会自动调用该函数,但是是在抛出异常的线程内执行
         */
        System.out.println(thread.getId() + "线程执行了...");
        System.out.println("被捕获的异常:" + exception);
    }

    public static void main(String[] args) {
        //传入继承ThreadGroup的类对象
        new Thread(new ThreadExceptionTest2(), () -> {
            //只能抛出运行时异常
            throw new NullPointerException();
        }).start();
    }
}