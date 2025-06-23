package com.one.ThreadDemo1;

/**
 * 定义一个吃货类 Eater
 */
public class Eater extends Thread {
    @Override
    public void run() {
        while (true) {
            // 使用同步代码块,当吃货在吃汉堡包时,厨师线程就无法执行
            synchronized (Hamburger.LOCK) {
                if (Hamburger.count == 0) {
                    break;
                } else {
                    if (Hamburger.flag) {
                        System.out.println("吃货正在吃汉堡包..");
                        Hamburger.flag = false;
                        Hamburger.count--;
                        // 没有汉堡包,吃货就通知厨师进行制作汉堡包
                        Hamburger.LOCK.notify();
                    } else {
                        // 吃货吃完之后,就进入等待状态,注意: wait()和notify()方法必须是锁对象调用
                        try {
                            Hamburger.LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
