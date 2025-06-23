package com.one.thread;

/**
 * @ClassName: MyThread
 * @Description: 创建多线程对象, 方法一: 实现Thread类
 * @Author: one
 * @Date: 2021/06/29
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println("MyThread2:" + i);
        }
    }
}
