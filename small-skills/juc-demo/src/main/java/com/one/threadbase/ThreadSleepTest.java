package com.one.threadbase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author one
 * @description sleep方法不是释放线程持有的锁对象
 * @date 2024-4-11
 */
public class ThreadSleepTest {

    private static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            AtomicInteger atomic = new AtomicInteger(i);
            Thread thread = new Thread(() -> {
                synchronized (object) {
                    // 必须获取了锁对象之后, 才能调用wait, notify, notifyAll等方法
                    object.notifyAll();
                    System.out.println("线程" + Thread.currentThread().getName() + "开始执行..");
//                    try {
//                        // sleep方法会使当前线程休眠指定时间, 不释放锁
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    // yield方法会使当前线程重回可执行状态, 等待CPU调度, 不释放锁
//                    Thread.yield();
                    try {
                        // wait方法会使当前线程等待, 会释放锁
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() + "执行结束..");
                }
            }, "线程" + atomic.get());
            thread.start();

//            // join方法会使当前线程等待某个线程执行结束再执行, 底层调用wait方法, 会释放锁
//            thread.join();
        }
    }
}
