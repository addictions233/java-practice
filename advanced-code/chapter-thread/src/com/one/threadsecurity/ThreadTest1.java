package com.one.threadsecurity;

/**
 *  模拟火车站窗口卖票案例,
 *  问题分析:
 *     问题1: 出现了不同窗口卖了同号票的原因
 *              窗口二卖了第100号票
 *              窗口一卖了第100号票
 *              窗口三卖了第100号票
 *          原因: 因为字符串拼接是很耗费时间(字符串拼接最好用StringBuilder),三个线程拿到了ticket=100做拼接打印时都还没有执行ticket--
 *          解决方法: 将 ticket--提到字符串拼接当中
 *     问题2: 出现卖了 0号和-1号票
 *             窗口二卖了第1号票
 *             窗口一卖了第0号票
 *             窗口三卖了第-1号票
 *          原因: 三个线程都是以ticket=1时进入循环判断,满足ticket>0,进行卖票打印,此时某一个线程执行了ticket--,此时ticket为0
 *          但是另外两个线程都进入了卖票过程,都将打印并执行ticket--,此时ticket会变成负数
 *          解决方法: 用synchronized解决线程安全问题
 */
public class ThreadTest1 {
    public static void main(String[] args) {
        Runnable mr = new MyRunnable1() ;

        // 用同一个Runnable对象 mr创建三个线程
        Thread t1 = new Thread(mr,"窗口一");
        Thread t2 = new Thread(mr,"窗口二");
        Thread t3 = new Thread(mr,"窗口三");
        //开启线程
        t1.start();
        t2.start();
        t3.start();
    }
}


