package com.one.sync;

import com.one.jmm.UnsafeFactory;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * @author Fox
 */
@Slf4j
public class SyncDemo {

      // volatile只能保证共享变量的可见性, 并不能保证操作的原子性
//    private static volatile int counter = 0;
//
//    public static void increment() {
//        counter++;
//    }
//
//    public static void decrement() {
//        counter--;
//    }

    private static int counter = 0;

//    /**
//     * 使用synchronized锁既能保证共享变量的可见性, 还能保证原子性和有序性
//     * 静态方法的锁对象是类的字节码对象
//     * 成员方法的锁对象是对象本身
//     */
//    public static synchronized void increment() {
//        counter++;
//    }
//
//    public static synchronized void decrement() {
//        counter--;
//    }
//
    /**
     * 自定义锁对象,任何对象都能作为synchronized的是锁对象, 依赖对象本身的monitor
     */
    private static String lock = "";

//    public static void increment() {
//        synchronized (lock){
//            counter++;
//        }
//    }

    /**
     * 使用synchronized本质上就是使用Monitor
     * 使用Unsafe#monitorEnter方法和Unsafe#monitorExit方法替换synchronized功能
     */
    public static void increment() {
        UnsafeFactory.getUnsafe().monitorEnter(lock);
        try {
            counter++;
        } finally {
            UnsafeFactory.getUnsafe().monitorExit(lock);
        }
    }


    public static void decrement() {
        synchronized (lock) {
            counter--;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                increment();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                decrement();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        //思考： counter=？
        log.info("counter={}", counter);
    }
}
