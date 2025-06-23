package com.one.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 *非公平自旋锁
 *实现一个自旋锁最朴素的思路就是直接利用CAS来保证加锁、解锁的原子性。具体到Java中，则可以利用原子类来实现。下面即是非公平自旋锁的实现，可以看到其实现过程非常简单。为了进一步支持锁重入，还提供了一个count变量记录锁重入的次数。但该实现的缺点也很明显，首先加锁线程需要不停自旋来检测锁的状态。此举会明显浪费CPU，当然这也是自旋锁方案的通用弊端；其次该实现方式是一个非公平的自旋锁，即无法保证公平性
 */
public class SpinLock {

    /**
     * 锁的持有者
     */
    private AtomicReference<Thread> owner = new AtomicReference<>();

    /**
     * 记录锁重入次数
     */
    private volatile int count = 0;

    public void lock() {
        Thread current = Thread.currentThread();
        // 当前线程已经持有锁, 则记录重入次数即可
        if( current == owner.get() ) {
            count++;
            return;
        }

        while ( !owner.compareAndSet(null, current) );
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if( current == owner.get() ) {
            if( count>0 ) {
                // 锁重入, 直接自减即可
                count--;
            } else {
                owner.set(null);
            }
        }
    }

}
