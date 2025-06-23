package com.one.threadsecurity;

/**
 *  注意: 新创建一个线程就是在新开辟一个栈空间,而堆空间只有一个,且所有的栈空间共享
 *  堆内存是线程共享的,栈内存是线程独有的
 *  开辟一个新的栈空间后,主线程就会往下继续走,不会等其它线程完毕后才往下走
 */
public class ThreadTest4 {
    public static void main(String[] args) {
        MyRunnable4 mr = new MyRunnable4();
        //创建三个线程
        Thread t1 = new Thread(mr,"窗口1:");
        Thread t2 = new Thread(mr,"窗口2:");
        Thread t3 = new Thread(mr,"窗口3:");

        t1.start();
        t2.start();
        t3.start();
    }
}
