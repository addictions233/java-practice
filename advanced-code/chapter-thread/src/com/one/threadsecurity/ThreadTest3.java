package com.one.threadsecurity;

/**
 * 用this为锁对象的同步方法和同步代码块共同执行火车站窗口卖票案例
 *      如果解决了多线程共享操作数据的不安全问题,那么就说明同步方法的锁对象是this
 */
public class ThreadTest3 {
    public static void main(String[] args) {
        MyRunnable3 mr = new MyRunnable3();
        //创建三个线程
        Thread t1 = new Thread(mr,"窗口一");
        Thread t2 = new Thread(mr,"窗口二");

        t1.start();
        t2.start();
    }
}
