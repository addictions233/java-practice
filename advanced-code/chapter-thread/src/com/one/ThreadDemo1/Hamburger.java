package com.one.ThreadDemo1;

/**
 *  线程之间通信用的是等待唤醒机制: wait()和notify()方法都是由控制线程执行的锁对象来执行的,又因为任意引用数据类型的java对象
 *  都能充当锁对象,所以任意对象对象都要具有wait()和notify()这个两个方法,所以这两个方法放在了Object类中
 *  Object类中的方法:
 *      void wait​() 导致当前线程等待，直到另一个线程调用该对象的 notify()方法或 notifyAll()方法
 *      void notify​() 唤醒正在等待对象监视器的单个线程。
 *      void notifyAll​() 唤醒正在等待对象监视器的所有线程
 */
public class Hamburger {
    /**
     * 布尔类型成员变量flag 当汉堡包还存在时 flag=true , 当汉堡包不存在时 flag =false;
     */
    public static boolean flag = false;

    /**
     * 成员变量 count 记录厨师做汉堡包次数
     */
    public static int count = 10;

    /**
     * 定义一个吃货和厨师共享的锁对象, 多个线程的锁对象要是同一个对象
     */
    public static final Object LOCK = new Object();

}
