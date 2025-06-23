package com.one.jmm;

import com.one.cas.UnsafeFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @author one
 * @description 测试多线程的可见性  JMM (java memory model) java内存模型
 *                               JUC(java util concurrent) java并发工具包
  * @date 2024-3-14
 */
public class VisibilityTest {
    /**
     * 线程A执行的标志,如果flag存在多线程的可见性问题,那么线程A将一直执行下去
     */
    private boolean flag = true;

    /**
     * volatile关键字的作用: 保证线程的可见性, 底层还是加lock前缀指令, 使用内存屏障storeLoad()
     */
//    private volatile boolean flag = true;

    private int count = 0;


    public static void main(String[] args) throws InterruptedException {
        VisibilityTest test = new VisibilityTest();

        Thread threadA = new Thread(() -> {
            test.load();
        }, "threadA");

        threadA.start();

        Thread.sleep(1000);
        // 线程B修改flag的值,使得线程A结束
        Thread threadB = new Thread(() -> {
            test.refresh();
        }, "threadB");
        threadB.start();
    }


    private void load() {
        System.out.println("线程" + Thread.currentThread().getName() + "开始执行了");
        while(flag) {
            count++;
            // 如果flag不能保证线程的可见性,那么线程将一直执行下去

            // java如何保证线程的可见性
            // 1.jvm层面 storeLoad内存屏障, lock
            // 2. 上下文切换 典型的 Thread.yield()方法

            //以下有几种方式可以使循环跳出
            // 1. 用volatile关键字修饰flag变量
            // 2.使用内存屏障也可以保证线程的可见性
            UnsafeFactory.getUnsafe().storeFence();
            // 3. #yield方法会让出CPU执行权, 切换上下文, 释放时间片 ,加载上下文 flag = false
            // 线程切换上下文会重新从主存中加载最新值
//            Thread.yield();  // 会切换线程上下文
            // 4. 使用synchronized关键字, 内存屏障
//            LockSupport.unpark(Thread.currentThread());
            // 5. #println方法会加synchronized锁 内存屏障
//            System.out.println(count);
            // 6. 等待1ms,共享变量的本地变量副本会失效, 跳出循环
//            shortWait(1000000);
        }
        System.out.println("线程" + Thread.currentThread().getName() + "执行结束了");
    }

    private void refresh() {
        // 修改flag的值
        this.flag = false;
        System.out.println("线程" + Thread.currentThread().getName() + "修改了线程的flag值:" + flag);
    }

    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }

}
