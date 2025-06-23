package com.one.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/05/17
 */
public class CountDownTest {

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(() ->  {
                try {
                    System.out.println(Thread.currentThread().getId() + "开始执行了...");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getId() + "执行结束了...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("主线程执行结束了....");
    }
}
