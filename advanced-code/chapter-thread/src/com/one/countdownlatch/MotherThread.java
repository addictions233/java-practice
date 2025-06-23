package com.one.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: MotherThread
 * @Description: 妈妈线程在所有的儿子线程执行结束之后才能结束
 * @Author: one
 * @Date: 2021/07/01
 */
public class MotherThread extends Thread {
    private CountDownLatch countDownLatch;

    public MotherThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            // 调用await()会一直阻塞等待,知道CountDownLatch计数器计数变为0,才会往下执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getName() + "---在收拾碗筷");
    }
}
