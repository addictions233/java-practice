package com.one.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fox
 * 同步执行
 */
public class ReentrantLockDemo {

    private static  int sum = 0;

    /**
     * ReentrantLock独占锁
     */
    private static Lock lock = new ReentrantLock();

    /**
     * 自定义的简单锁
     */
//    private static TulingLock lock = new TulingLock();

    public static void main(String[] args) throws InterruptedException {
        // ReentrantLock锁的实现原理: 三个线程并发请求处理
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                //加锁
                lock.lock();
                try {
                    // TODO 业务逻辑：读写操作不能保证线程安全
                    for (int j = 0; j < 10000; j++) {
                        sum++;
                    }
                } finally {
                    // 解锁
                    lock.unlock();
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println(sum);
    }
}
