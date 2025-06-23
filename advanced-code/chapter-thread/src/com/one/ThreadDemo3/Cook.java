package com.one.ThreadDemo3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName: Cook
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/05
 */
public class Cook extends Thread {
    private ArrayBlockingQueue<String> queue;

    private int count;

    /**
     * 构造方法
     * @param queue
     */
    public Cook(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            if( count == 10) {
                break;
            } else {
                try {
                    // 因为阻塞队列中的take()方法已经添加了ReentrantLock锁对象,所以不需要额外添加锁
                    String take = queue.take();
                    System.out.println("吃货吃了" + take);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
