package com.one.sync;

import java.util.concurrent.locks.Lock;

/**
 * @description: Synchronized能解决并发的三大特性: 原子性,可见性, 有序性
 * @author: wanjunjie
 * @date: 2024/04/12
 */
public class SynchronizedTest {

    /**
     * volatile只能保证变量的可见性, 不能保证操作的原子性
     */
    private volatile static int num = 0;

    /**
     * 使用synchronized锁可以保证操作的原子性
     */
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < 5000; i++) {
                    num++;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                for (int j = 0; j < 5000; j++) {
                    num--;
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("最后结果:" + num);
    }
}
