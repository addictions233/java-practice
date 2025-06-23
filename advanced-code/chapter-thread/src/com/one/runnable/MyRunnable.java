package com.one.runnable;

/**
 * @ClassName: MyRunnable
 * @Description: 如果是直接继承Thread类创建多线程对象,那么线程对象和任务就绑定在一起了,如果是
 *               实现Runnable接口,那么线程对象和线程对象要执行的线程任务就分离了
 * @Author: one
 * @Date: 2021/06/29
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
