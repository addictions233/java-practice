package com.one.volatileDemo;

/**
 * volatile关键字:  1,保证了变量在内存中的可见性
 *                 2, 防止了指令重排
 * @author one
 */
public class Money {
//    public static double money = 100000;
    /**
     * 线程栈在使用volatile关键字修饰的成员变量时,每次都会去堆内存中核对该变量是否与本地线程栈中的变量副本相等
     * 不加volatile,userMoney1线程陷入死循环,因为money吧变量在该线程是不可能见的,一直都是100000
     * 加了volatile保证了money变量的内存可见性,userMoney1线程可以感受到money变量被修改,然后退出死循环
     */
    public volatile static double money = 100000;

    public static final Object LOCK = new Object();
}
