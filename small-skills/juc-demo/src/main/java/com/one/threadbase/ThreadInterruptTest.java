package com.one.threadbase;

import java.util.concurrent.locks.LockSupport;

/**
 * @author  Fox
 * 中断机制: 如果我们手动调用Thread.interrupt()方法, 仅仅只是将该线程的中断位标识置为false,并不会真正的停止该线程
 * 如果我们想要停止一个线程, 需要写一个不断检查当前线程标准位的操作, 如果检查到标志位为true, 就表示退出该线程
 */
public class  ThreadInterruptTest {

    static int i = 0;

    public static void main(String[] args)  {
        System.out.println("begin");
        Thread t1 = new Thread(() -> {
            while (true) {
                i++;
                System.out.println(i);
                try {
                    // 当线程阻塞在#sleep方法时, 调用了#interrupt方法会抛出InterruptedException异常, 且中断标志位置为false
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Thread.interrupted();  // 清除中断标志位
                // Thread.currentThread().isInterrupted(); // 不会清除中断标志位, 只会返回这个线程的中断位标识
                if (Thread.interrupted()) { // 返回该现场的中断位标识, 并将这个线程的中断位标识置为false
                    System.out.println("========="); // 如果#sleep方法抛出异常, 并将中断位标识置为false,那么这里不会打印
                }
                if(i==10){
                    break;
                }
            }
        });

        t1.start();
        // 哪个线程对象调用了#interrupt方法, 那么这个线程的中断状态会被置为true
        // 如果该线程阻塞在 #wait #join #sleep等方法时, 此时会抛出InterruptedException异常, 且中断标志位置为false
        // 不会停止线程t1,只会设置一个中断标志位 flag=true
        // 使用共享变量实现线程之间通信
        t1.interrupt();
        System.out.println("t1线程是否中断:" + t1.isInterrupted());

    }
}
