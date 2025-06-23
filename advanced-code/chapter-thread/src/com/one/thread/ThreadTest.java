package com.one.thread;
/**
 * 只用Thread的派生类的对象才是线程对象, 实现Runnable接口和Callable接口的类的对象不是线程对象
 *  第一种实现多线程方式: 继承Thread类,重写run()方法, run()方法得分方法体就是线程要执行的任务
 */
public class ThreadTest {
    public static void main(String[] args) {
        //用匿名内部类的方式自定义线程
        Thread myThread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("MyThread1:" + i);
                }
            }
        };
        // 启动自定义线程
        myThread1.start();

        // 第二个自定义线程
        MyThread myThread = new MyThread();
        myThread.start();

        //主线程
        for (int i = 1; i <= 100; i++) {
            System.out.println("main:"+i);
        }

    }
}

