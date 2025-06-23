package com.one.ThreadDemo3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName: Foodie
 * @Description: TODO
 * @Author: one
 * @Date: 2021/07/05
 */
public class Foodie extends Thread {
    private ArrayBlockingQueue<String> queue;

    public Foodie(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("厨师正在做汉堡包");
            try {
                // 因为阻塞队列中的put()方法已经添加了ReentrantLock锁对象,所以不需要额外添加锁
                queue.put("汉堡包");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
