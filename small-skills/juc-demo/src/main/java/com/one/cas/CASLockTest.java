package com.one.cas;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author one
 * @description CAS是乐观锁, 是用户态级别的一种锁机制(避免切换到内核态), 但是如果CAS的锁冲突概率比较高, 就会造成CPU空转
 *              所以如果线程间锁冲突的概率比较高, 不适合使用CAS, 而要使用悲观锁
 * @date 2024-4-10
 */
public class CASLockTest {

    /**
     *
     */
    private volatile static int sum = 0;

    static Object object = "";
    private static ReentrantLock lock = new ReentrantLock();

    private static CASLock casLock = new CASLock();


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    // 使用CAS自旋锁
                    if (casLock.getState() == 0 && casLock.cas()) {
                        try {
                            for (int j = 0; j < 10000; j++) {
                                sum++;
                            }
                            System.out.println(casLock.getState());

                        } finally {
                            casLock.setState(0);
                        }
                        break;
                    }
                }

            }).start();
        }
        // 等待异步线程执行结束
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sum);

    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                synchronized (object) {
//                    for (int j = 0; j < 10000; j++) {
//                        sum++;
//                    }
//                }
//            }).start();
//        }
//        // 等待异步线程执行结束
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(sum);
//
//    }


//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                lock.lock();
//                try {
//                    for (int j = 0; j < 10000; j++) {
//                        sum++;
//                    }
//                } finally {
//                    lock.unlock();
//                }
//            }).start();
//        }
//        // 等待异步线程执行结束
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(sum);
//
//    }
}
