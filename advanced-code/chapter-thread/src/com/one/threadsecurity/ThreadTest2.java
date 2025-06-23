package com.one.threadsecurity;

/**
 *  注意: 新创建一个线程就是在新开辟一个栈空间,而堆空间只有一个,且所有的栈空间共享
 *  开辟一个新的栈空间后,主线程就会往下继续走,不会等其它线程完毕后才往下走
 */
public class ThreadTest2 {
    public static void main(String[] args) {
        // 因为有且仅创建一个MyRunnable2类的对象,即任务对象有且仅有一个,所以三个线程对象的锁对象是同一MyRunnable2对象的成员变量lock
        MyRunnable2 mr = new MyRunnable2();
        //创建三个线程
        Thread t1 = new Thread(mr,"窗口1:");
        Thread t2 = new Thread(mr,"窗口2:");
        Thread t3 = new Thread(mr,"窗口3:");

        t1.start();
        t2.start();
        t3.start();
    }
}
