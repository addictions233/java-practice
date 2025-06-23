package com.one.ThreadDemo2;

/**
 * @ClassName: Foodie
 * @Description: 吃货线程
 * @Author: one
 * @Date: 2021/07/05
 */
public class Foodie extends Thread {
    /**
     * 包子对象是作为线程锁对象
     */
    private BaoZi baoZi;

    public int getCount() {
        return count;
    }

    /**
     * 统计一共吃了多少个包子
     */
    private int count;

    /**
     * 构造方法
     *
     * @param name  线程名称
     * @param baoZi 包子对象
     */
    public Foodie(String name, BaoZi baoZi) {
        super(name);
        this.baoZi = baoZi;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.baoZi) {
                if (baoZi.isFlag()) {
                    System.out.println(getName() + "正在吃包子");
                    try {
                        // 吃包子需要一秒钟,进行限时等待状态
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    baoZi.setFlag(false);
                    System.out.println(getName() + "总共吃了" + count + "个包子");
                    // 唤醒厨师线程做包子
                    baoZi.notify();
                } else {
                    // 如果没有包子,吃货线程就进入等待状态
                    try {
                        baoZi.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
