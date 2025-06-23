package com.one.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: SonThread3
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/01
 */
public class SonThread3 extends Thread {
    private CountDownLatch countDownLatch;

    public SonThread3(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(getName() + "吃了第" + i + "个饺子");
        }
        // 当现场执行结束后,需要让线程计数器减去1
        countDownLatch.countDown();
    }
}
