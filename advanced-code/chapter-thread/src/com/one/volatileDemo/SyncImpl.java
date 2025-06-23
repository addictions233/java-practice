package com.one.volatileDemo;

/**
 * @ClassName: syncImpl
 * @Description: 用synchronized的锁来实现volatile关键字的效果
 * @Author: one
 * @Date: 2021/07/12
 */
public class SyncImpl {
    public static void main(String[] args) {
        Thread thread1 = new SyncUserMoney1();
        Thread thread2 = new SyncUserMoney2();
        thread1.start();
        thread2.start();
    }
}
