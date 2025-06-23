package com.one.volatileDemo;

/**
 * 多个线程是如何使用共享数据的?
 *   Money类的静态成员变量 money=100000存储在方法区中
 *   而类UerMoneys1和类UserMoney2是如何在run()方法中调用成员变量money的?
 *      1,先获取共享数据(成员变量money)
 *      2,将共享数据的值在各自的线程栈中临时存储一个该变量副本
 *      3,当方法要使用共享数据时就去变量副本中拿取变量副本存储的值
 *      优点是: 栈内存方法中获取堆内存的成员变量值速度更快
 *      缺点是: 存在栈内存中的变量副本和堆内存中的实际数据不一致的情况(线程安全问题)
 */
public class UseMoney1 extends Thread {
    @Override
    public void run(){
        while(Money.money == 100000){

        };
        // 当money金额发送改变时,本线程需要打印下面这句话
        System.out.println("钱已经被改变了");
    }
}
