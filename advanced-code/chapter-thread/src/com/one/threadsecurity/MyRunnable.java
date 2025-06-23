package com.one.threadsecurity;

/**
 * @ClassName: MyRunnable
 * @Description: 线程安全问题: 模拟火车站3个窗口卖100张票的案例
 * @Author: one
 * @Date: 2021/06/30
 */
public class MyRunnable implements Runnable {
    /**
     * 静态成员变量存放在方法区中,是该类的所有对象共享的,
     *      如果使用继承Thread类的方式创建三个线程对象,想要三个线程对象共同卖100张票,所以要定义的ticket必须为静态成员变量
     * 对象的成员变量放在堆内存中,是所用线程栈贡献的
     *      如果采用的实现Runnable接口的方式创建三个线程对象,只用创建一个Runnable对象,然后将同一线程任务分配给三个Thread对象
     *      所用ticket只用定义为普通成员变量,不用定义为静态的
     */
    private  int ticket = 100;
    @Override
    public void run() {
        while (true) {
            if( ticket <= 0) {
                System.out.println("票卖完了");
                break;
            } else {
                System.out.println(Thread.currentThread().getName() + "卖了第" + ticket + "张票");
                ticket--;
            }
        }
    }
}
