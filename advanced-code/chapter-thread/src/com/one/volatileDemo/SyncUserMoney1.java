package com.one.volatileDemo;

/**
 * @ClassName: SyncUserMoney1
 * @Description: synchronized关键字能保证线程栈中使用的数据是堆内存中的最新数据,使用锁对象时会清空线程栈中的变量副本
 *               然后去堆内存中获取到最新值
 * @Author: one
 * @Date: 2021/07/12
 */
public class SyncUserMoney1 extends Thread {
    @Override
    public void run() {
        synchronized (Money.LOCK) {
            while (Money.money != 100000) {

            }
            System.out.println("钱被花了..");
        }
    }
}
