package com.one.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: AtomClass
 * @Description: 原子类: AtomInteger AtomLong等,
 * 原子类的实现原理是采用CAS自旋算法(乐观锁),效率比synchronized(悲观锁)高
 * 乐观锁: volatile(内存可见性) + CAS自旋算法 实现
 * 悲观锁:  synchronized, ReentrantLock 实现
 * ReentrantLock与synchronized很相似，它们都具备一样的线程重入特性。
 * synchronized是在JVM层面上实现的,不但可以通过一些监控工具监控synchronized的锁定,而且在代码执行时出现异常，JVM会自动释放锁定，
 * 但是使用Lock则不行，lock是通过代码实现的，要保证锁定一定会被释放，就必须将unLock()放到finally{}中

 * @Author: one
 * @Date: 2021/07/13
 */
public class AtomClass {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        // 创建1000个线程
        for (int i = 0; i < 1000; i++) {
            // 每个线程送1000个冰淇淋
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    int value = atomicInteger.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "送出了第" + value + "个冰淇淋" );
                }
            }).start();
        }
    }
}
