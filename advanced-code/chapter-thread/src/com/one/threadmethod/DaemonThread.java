package com.one.threadmethod;

/**
 *  Thread类中的方法:
 *      void setDaemon​(boolean on)   // 守护线程
 *          将此线程标记为 daemon线程或用户线程。
 *   守护线程特点:
 *      当普通线程结束后,守护线程不管执行完没都会结束,但不是立即结束,守护线程还会执行一会儿
 */
public class DaemonThread {
    public static void main(String[] args) {
        //自定义线程
        Thread thread1 = new Thread(()-> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
        });
        //将自定义线程设置为主线程的守护线程
        thread1.setDaemon(true);
        //开启守护线程
        thread1.start();

        //主线程
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"---"+i);
        }

    }
}
