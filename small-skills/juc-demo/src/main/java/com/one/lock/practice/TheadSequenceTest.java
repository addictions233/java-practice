package com.one.lock.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 练习: 使用三个线程循环顺序打印A,B,C
 * @author: wanjunjie
 * @date: 2025/03/27
 */
public class TheadSequenceTest {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        Condition condition3 = reentrantLock.newCondition();
        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    condition2.signal();
                    System.out.println("A");
                    Thread.sleep(100);
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    reentrantLock.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                reentrantLock.lock();
                try {
                    condition3.signal();
                    System.out.println("B");
                    Thread.sleep(100);
                    condition2.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(() -> {
            while(true) {
                reentrantLock.lock();
                try {
                    condition.signal();
                    System.out.println("C");
                    Thread.sleep(100);
                    condition3.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    reentrantLock.unlock();
                }
            }
        }).start();
    }
}
