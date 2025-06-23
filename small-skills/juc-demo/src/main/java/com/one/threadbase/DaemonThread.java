package com.one.threadbase;

/**
 * @author one
 * @description 守护线程
 * Daemon（守护） 线程是一种支持型线程，因为它主要被用作程序中后台调 度以及支持性工作。
 * 这意味着，当一个 Java 虚拟机中不存在非 Daemon 线程的 时候， Java 虚拟机将会退出。
 * 可以通过调用 Thread.setDaemon(true)将线程设置 为 Daemon 线程。我们一般用不上，比如垃圾回收线程就是 Daemon 线程。
 * Daemon 线程被用作完成支持性工作， 但是在 Java 虚拟机退出时 Daemon 线程中的 finally 块并不一定会执行。
 * 在构建 Daemon 线程时， 不能依靠 finally 块中 的内容来确保执行关闭或清理资源的逻辑
 * @date 2024-12-17
 */
public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        // 设置线程为守护线程
//        myThread.setDaemon(Boolean.TRUE);
        myThread.start();
        // 如果线程是守护线程, 那么当用户线程执行结束之后, 守护线程也会立马结束
        // 如果线程是用户线程, 那么主线程执行结束之后, 用户线程还是会继续执行
        Thread.sleep(100);
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("自定义线程执行任务....");
            while (true) {
                System.out.println(Thread.currentThread().getName() + "在执行...");
            }
        }
    }
}
