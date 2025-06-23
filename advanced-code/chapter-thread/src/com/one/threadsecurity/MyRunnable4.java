package com.one.threadsecurity;

import java.util.concurrent.locks.ReentrantLock;

/**
 *  同步代码块的锁对象是自动关闭和自动开启的,我们并不知道是如何加锁和释放锁对象的,
 *  为了更方便的表达如何加锁和释放锁对象,JDK5之后提供了一个新的锁对象Lock
 */
public class MyRunnable4 implements Runnable {
    private int ticket = 100;
    //创建三个卖票线程共同用的锁对象,锁对象用final修饰,表示对象变量中存储的地址值不会改变
//    final Object lock = new Object();

    /**
     *  Lock是一个接口,所以得用该接口的实现类ReentrantLock来创建对象
     */
    ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        while(true){
            // 获取锁
            lock.lock();
            try {
                if(ticket<=0){
                    break;
                } else{
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName()+"卖了第"+(ticket--)+"号票");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally{
                // 除非虚拟机退出,否则finally中的代码一定会执行
                // 一定要释放锁,否则其它线程无法获取锁,造成死锁
                lock.unlock();
            }
        }
    }
}
