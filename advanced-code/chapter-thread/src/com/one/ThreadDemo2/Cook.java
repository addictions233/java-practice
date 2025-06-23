package com.one.ThreadDemo2;

/**
 * @ClassName: Cook
 * @Description: 厨师线程
 * @Author: one
 * @Date: 2021/07/05
 */
public class Cook extends Thread {
    /**
     * baoZi对象是作为Cook线程和Foodie线程的锁对象
     */
    private BaoZi baoZi;

    /**
     * 重写父类构造方法
     *
     * @param name  线程名称
     * @param baoZi 包子对象
     */
    public Cook(String name, BaoZi baoZi) {
        super(name);
        this.baoZi = baoZi;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.baoZi) {
                if (baoZi.isFlag()) {
                    // 如果包子存在,厨师线程就进行等待状态
                    try {
                        baoZi.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(getName() + "正在做包子");
                    try {
                        // 厨师线程做包子需要3秒钟,进行限时等待状态
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    baoZi.setFlag(true);
                    // 通知吃货线程吃包子
                    baoZi.notify();
                }
            }
        }
    }
}
