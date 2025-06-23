package com.one.threadsecurity;

/**
 * @ClassName: Main
 * @Description: TODO
 * @Author: one
 * @Date: 2021/06/30
 */
public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable1 = new MyRunnable();
        Thread thread1 = new Thread(myRunnable1,"窗口一");

        Thread thread2 = new Thread(myRunnable1,"窗口二");

        Thread thread3 = new Thread(myRunnable1,"窗口三");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
