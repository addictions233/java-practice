package com.one.threadmethod;
/**
 * Thread类中的成员方法:
 *      static Thread currentThread​()
 *          静态方法,返回对当前正在执行的线程对象的引用。
 *
 *      String getName​()
 *          返回此线程的名称。
 *
 *      void setName​(String name)
 *          将此线程的名称更改为等于参数 name
 */
public class CurrentThreadMethod {
    public static void main(String[] args) {
        //自定义线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("自定义线程"); //设置线程名字
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName()+"---"+i); //打印线程名字
                }
            }
        }).start();


        //主线程
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"----"+i); //获取主线程名字
        }
    }
}
