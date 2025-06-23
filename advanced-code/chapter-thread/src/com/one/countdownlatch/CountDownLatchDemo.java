package com.one.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: CountDownLatchDemo
 * @Description: 让一个线程在其它三个线程执行结束之后才能结束
 * @Author: one
 * @Date: 2021/07/01
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        // CountDownLatch就相当于一个线程的执行技术器, 形参就是需要等待的线程数
        CountDownLatch countDownLatch = new CountDownLatch(3);

        MotherThread motherThread = new MotherThread(countDownLatch);
        motherThread.setName("妈妈");
        motherThread.start();

        SonThread1 sonThread1 = new SonThread1(countDownLatch);
        sonThread1.setName("儿子一");
        sonThread1.start();

        SonThread2 sonThread2 = new SonThread2(countDownLatch);
        sonThread2.setName("儿子二");
        sonThread2.start();

        SonThread3 sonThread3 = new SonThread3(countDownLatch);
        sonThread3.setName("儿子三");
        sonThread3.start();
    }
}
