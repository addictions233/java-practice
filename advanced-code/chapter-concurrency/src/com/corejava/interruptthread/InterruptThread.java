package com.corejava.interruptthread;

import java.util.LinkedList;

public class InterruptThread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName()+":"+i);
                    Thread.currentThread().interrupt();
                    System.out.println(Thread.currentThread().isInterrupted());
//                    System.out.println("线程已经中断");
                }
            }
        }).start();
    }
}
