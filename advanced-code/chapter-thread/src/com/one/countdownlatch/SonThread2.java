package com.one.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: SonThread2
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/01
 */
public class SonThread2 extends Thread {
    private CountDownLatch countDownLatch;

    public SonThread2(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 15; i++) {
            System.out.println(getName() + "吃了第" + i + "个饺子");
        }
        countDownLatch.countDown();
    }
}
