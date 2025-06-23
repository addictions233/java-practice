package com.one.volatileDemo;

/**
 * @ClassName: SyncUserMoney2
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/12
 */
public class SyncUserMoney2 extends Thread {
    @Override
    public void run() {
        synchronized (Money.LOCK) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Money.money -= 1000;
            System.out.println(Thread.currentThread().getName() + "花了1000块");
        }
    }
}
