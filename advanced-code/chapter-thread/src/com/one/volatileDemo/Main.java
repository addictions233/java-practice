package com.one.volatileDemo;

public class Main {
    public static void main(String[] args) {
        Thread thread1 = new UseMoney1();
        thread1.setName("线程一");
        thread1.start();
        Thread thread2 = new UseMoney2();
        thread2.setName("线程二");
        thread2.start();
    }
}
