package com.one.threadsecurity;

/**
 * @ClassName: DeadLock
 * @Description: 使用多个锁进行嵌套,容易造成死锁现象
 * @Author: one
 * @Date: 2021/07/01
 */
public class DeadLock {
    public static void main(String[] args) {
        // 创建第一把锁对象
        Object objectA = new Object();
        // 创建第二步锁对象
        Object objectB = new Object();

        /**
         * 使用synchronized是在进入同步方法或者同步代码块时获得锁
         * 在方法或者代码块执行结束后才释放锁,如果方法或者代码块没有执行结束是不会释放锁对象的
         */
        Thread thread1 = new Thread( () -> {
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + "开始执行了....");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + "执行结束了...");
                }
            }
        },"线程1");

        Thread thread2 = new Thread( () -> {
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + "开始执行了....");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + "执行结束了...");
                }
            }
        },"线程2");

        //开启线程
        thread1.start();
        thread2.start();
    }
}
