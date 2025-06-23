package com.one.atomic;

/**
 * @ClassName: MyAtomThread
 * @Description: 原子性: 是指多个操作不能分割,要么全部都执行,要么全部不执行
 *               volatile只能保证每次线程中读到的堆内存中数据是最新的(内存可见性),但是并不能保证多个操作的原子性(互斥性)
 *               synchronized能保证操作的原子性,因为使用锁对象会限制其他线程对数据的访问
 * @Author: one
 * @Date: 2021/07/12
 */
public class MyAtomThread implements Runnable{
    /**
     * volatile只能保证变量在内存中的可见性,不能保证操作的原子性
     */
     private volatile int count = 0;

    /**
     * 每个线程送出一千个冰淇淋
     */
    @Override
    public void run() {
        for (int i = 1; i <= 1000; i++) {
            count++;
            System.out.println(Thread.currentThread().getName() + "送出了第" + count + "个冰淇淋");
        }
    }
}
