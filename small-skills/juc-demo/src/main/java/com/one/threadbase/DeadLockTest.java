package com.one.threadbase;

import sun.plugin2.main.server.HeartbeatThread;

/**
 * @author one
 * @description 测试死锁
 * @date 2024-12-22
 */
public class DeadLockTest {



    public static void main(String[] args) throws Exception {

        Object lock1 = new Object();

        Object lock2 = new Object();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println("执行任务1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (lock2) {
                        System.out.println("执行任务2");
                    }
                }
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                synchronized (lock2) {
                    System.out.println("执行任务3");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (lock1) {
                        System.out.println("执行任务4");
                    }
                }
            }
        };

        thread1.start();
        thread2.start();
    }
}
