package com.one.threadpool.exceptionhandle;

/**
 * @author one
 *  ThreadGroup实现自Thread的内部接口UncaughtExceptionHandler。
 *  所以要定义自己的异步线程异常处理器可以继承ThreadGroup类,
 */
public class MyThreadGroup extends ThreadGroup {

    private MyThreadGroup(){
        super("ThreadTest");
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        // 当线程抛出运行时异常时,系统会自动调用该函数,但是是在抛出异常的线程内执行
        System.out.println(thread.getId() + "线程执行了...");
        System.out.println("被捕获的异常:" + exception);
    }

    public static void main(String[] args) {
        //传入继承ThreadGroup的类对象
        new Thread(new MyThreadGroup(), () -> {
            //只能抛出运行时异常
            throw new NullPointerException();
        }).start();
    }
}