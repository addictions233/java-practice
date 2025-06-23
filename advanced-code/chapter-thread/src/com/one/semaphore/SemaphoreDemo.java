package com.one.semaphore;

/**
 * @ClassName: SemaphoreDemo
 * @Description: Semaphore类用来指定同时访问资源的最大线程数量, 使用方式类似于 ReentrantLock
 * @Author: one
 * @Date: 2021/07/01
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Runnable myRunnable = new MyRunnable();
        for (int i = 0; i < 100; i++) {
            // 每次最多只有两个线程在执行
            new Thread(myRunnable).start();
        }
    }
}
