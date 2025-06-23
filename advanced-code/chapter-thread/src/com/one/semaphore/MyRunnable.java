package com.one.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @ClassName: MyRunnable
 * @Description: Semaphore作用: 限定访问指定资源的线程数量,同一时间执行的线程数量不能超过给定数量
 * @Author: one
 * @Date: 2021/07/01
 */
public class MyRunnable implements Runnable {
    /**
     * 构造方法中的参数: 指定同时执行的最大线程数, Semaphore对象的使用类似于ReentrantLock
     */
    private Semaphore semaphore = new Semaphore(2);
    @Override
    public void run() {
        try {
            // 线程获取执行权, 类似于 ReentrantLock.lock()方法
            // 如果线程没有获取到执行权,会一直阻塞在这里
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + "开始执行了");
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "释放了执行资格");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 线程释放执行权 类似于 ReentrantLock.release()方法
            semaphore.release();
        }
    }
}
