package com.one.volatileDemo;

public class UseMoney2 extends Thread {
    @Override
    public void run(){
        try {
            Thread.sleep(1000);
            Money.money -= 10000;
            System.out.println(Thread.currentThread().getName() + "已经使用了10000块钱");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
