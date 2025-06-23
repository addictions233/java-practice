package com.one.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: SonThread1
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/01
 */
public class SonThread1 extends Thread {
    private CountDownLatch countDownLatch;

    public SonThread1(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + "吃了第" + i + "个饺子");
        }
        //当本线程执行结束后,需要让线程计数器减1
        countDownLatch.countDown();
    }
}
