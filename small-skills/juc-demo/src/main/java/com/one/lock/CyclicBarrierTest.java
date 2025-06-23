package com.one.lock;

import java.util.concurrent.CyclicBarrier;

/**
 * @author one
 * @description 循环屏障demo
 * @date 2024-5-19
 */
public class CyclicBarrierTest {
    private static  CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "开始等待其他线程");
                try {
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + "执行结束");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
