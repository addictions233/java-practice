package com.one.ThreadDemo1;

/**
 * 厨师类 Cook 当没有汉堡包是负责做汉堡包
 */
public class Cook extends Thread {
    @Override
    public void run() {
        while (true) {
            // 使用同步代码块控制厨师和吃货两个线程轮流执行
            synchronized (Hamburger.LOCK) {
                // 如果做的汉堡包数量够10个,就跳出循环
                if (Hamburger.count == 0) {
                    break;
                } else {
                    if (Hamburger.flag) {
                        // 如果还有汉堡包,厨师线程就进入等待状态
                        try {
                            Hamburger.LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 如果没有了汉堡包
                        System.out.println("厨师正在烹饪汉堡包");
                        Hamburger.flag = true;
                        // 生产好了汉堡包,就唤醒吃货线程
                        Hamburger.LOCK.notify();
                    }
                }
            }
        }
    }

}
