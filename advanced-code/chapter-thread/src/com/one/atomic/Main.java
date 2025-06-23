package com.one.atomic;

/**
 * @ClassName: Main
 * @Description: 测试多个操作的原子性
 * @Author: one
 * @Date: 2021/07/12
 */
public class Main {
    public static void main(String[] args) {
        Runnable myRunnable = new MyAtomThread();
        // 测试表明: 不用原子类会存在线程安全问题
        // 一千个线程执行任务
        for (int i = 0; i < 1000; i++) {
            new Thread(myRunnable).start();
        }
    }
}
