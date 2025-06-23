package com.one.sync;

/**
 * @description: synchronized是通过对象内部的一个叫做监视器锁(monitor)来实现的, 但是监视器本质又是依赖操作系统底层的
 *               Mutex lock来实现的, 但是由于使用Mutex Lock需要将当前现场挂起并从用户态切换到内核态来执行, 这种切换的
 *               代价是非常昂贵的, 这种依赖于操作系统的Mutex Lock来实现的锁我们称为重量级锁
 * @author: wanjunjie
 * @date: 2024/04/12
 */
public class LockTest {
    /**
     * thread1获取了锁
     * thread1释放了锁
     * thread3获取了锁
     * thread3释放了锁
     * thread2获取了锁
     * thread2释放了锁
     */
    private static Object lock = new Object();


    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                try {
                    lock.wait(); // wait方法会释放持有的锁
                    Thread.sleep(3000); // sleep方法不释放持有的锁
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        },"thread1").start();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                try {
                    lock.notifyAll();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        },"thread2").start();

        new Thread(() -> {
            synchronized (lock) { // 由于获取锁的等待队列是栈的数据结构(后进先出),所以线程3比线程2先获取锁
                System.out.println(Thread.currentThread().getName() + "获取了锁");
                try {
//                    lock.notifyAll();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "释放了锁");
            }
        },"thread3").start();


//        new Thread(() -> {
//            synchronized (lock) {
//                System.out.println(Thread.currentThread().getName() + "获取了锁");
//                try {
//                    lock.notifyAll();
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println(Thread.currentThread().getName() + "释放了锁");
//            }
//        },"thread4").start();
    }
}
