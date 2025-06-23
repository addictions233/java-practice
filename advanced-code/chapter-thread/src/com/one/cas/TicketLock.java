package com.one.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公平自旋锁
 * 为了解决实现自旋锁的公平性，我们需要额外引入一个排队机制。具体地，分别用两个原子变量表示排队号码、当前服务号码（即持有锁的号码）。基本原理类似于银行服务窗口的取号、叫号机制。加锁过程：会先给当前线程分配一个排队号码，然后该线程开始自旋。直到被它叫到号才退出自旋，即它的排队号码等于当前服务号码。解锁过程：计算、更新当前服务号码的值。以便下一个线程能够结束自旋、获取到锁。下面即是一个基于排队机制的公平自旋锁实现，可以看到每次调用requestTicketNum方法即可获得一个单调递增的排队号码，并通过一个ThreadLocal类型的threadLocalNum变量来保存各线程所获得的排队号码。进一步地，这里还增加了一个count变量以实现锁重入
 */
public class TicketLock {

    /**
     * 当前持有锁的号码
     */
    private AtomicInteger serviceNum = new AtomicInteger(0);

    /**
     * 记录锁重入次数
     */
    private volatile int count = 0;

    /**
     * 排队号码
     */
    private AtomicInteger ticketNum = new AtomicInteger(0);

    /**
     * 各线程存放自己所申请的排队号码
     */
    private static ThreadLocal<Integer> threadLocalNum = new ThreadLocal<>();

    public void lock() {
        Integer num = threadLocalNum.get();
        if( num!=null && num==serviceNum.get() ) {
            // 当前线程已经持有锁, 则记录重入次数即可
            count++;
            return;
        }

        // 申请一个排队号码
        num = requestTicketNum();
        threadLocalNum.set( num );
        // 自旋等待, 直到该排队号码与serviceNum相等
        while ( num != this.serviceNum.get() );
    }

    public void unlock() {
        Integer num = threadLocalNum.get();
        if( num!=null && num==serviceNum.get() ) {
            if( count>0 ) {
                // 锁重入, 直接自减即可
                count--;
            } else {
                threadLocalNum.remove();
                // 自增serviceNum, 以便下一个排队号码的线程能够退出自旋
                serviceNum.set( num+1 );
            }
        }
    }

    /**
     * 申请一个排队号码
     */
    private int requestTicketNum() {
        return ticketNum.getAndIncrement();
    }

}