package com.one.runnable;

/**
 *  第二种实现多线程方式: 实现Runnable接口
 *  @FunctionalInterface Runnable是函数式接口,所以可以用lambda表达式重写该接口方法
 */
public class RunnableDemo1 {
    public static void main(String[] args) {
        //自定义线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName()+":helloworld");
                }
            }
        }).start();

        Runnable myRunnable = new MyRunnable();
        Thread myThread = new Thread(myRunnable);
        myThread.start();

        //主线程
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+":php");
        }
    }
}
