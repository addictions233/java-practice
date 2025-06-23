package com.one.threadsecurity;
/**
 *  用同步代码块对火车站卖车票案例进行改进,解决线程安全问题
 *      同步代码块解决线程不安全问题?
 *          当多个线程需要访问同一个数据时,我们将访问和操作该数据的代码块添加一个锁,
 *          只有当一个线程拿到锁对象时才能解开这个锁执行修改数据代码,当且因为锁对象唯一时,
 *          这样我们就能保证任何情况下都只能有一个线程能拿到锁对象并执行修改数据代码块
 *      锁对象的要求: 可以是任意引用数据类型的对象, 但是要求多个线程使用的锁对象必须是同一个对象
 */
public class MyRunnable2 implements Runnable {
    private int ticket = 100;
    /**
     * 创建三个卖票线程共同用的锁对象,锁对象用final修饰,表示对象变量中存储的地址值不会改变
     * 多个线程使用的必须是同一个锁对象,由于任务对象有且仅创建一个,所以成员变量lock有且只用一个,且是线程对象共享的
     */
    final Object lock = new Object();
    @Override
    public void run() {
        while(true){
            /**
             * 使用synchronized的同步代码块,进入同步代码块就必须获得锁,当同步代码块中的代码执行结束后就释放锁资源
             * 并没有显性的获取锁和释放锁
             */
            synchronized (lock) {
                if(ticket<=0){
                    break;
                } else{
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"卖了第"+(ticket--)+"号票");
                }
            }
        }
    }
}
