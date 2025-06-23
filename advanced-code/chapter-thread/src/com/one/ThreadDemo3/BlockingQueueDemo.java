package com.one.ThreadDemo3;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ClassName: BlockingQueueDemo
 * @Description: 阻塞队列的两种实现类: ArrayBlockingQueue 底层是数组, 队列长度有限
 *                                    LinkedBlockingQueue 底层是链表, 队列长度没有限制, 但是不能超过int
 * @Author: one
 * @Date: 2021/07/05
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // 要指定阻塞队列的长度
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        arrayBlockingQueue.put("aaaa");
        System.out.println(arrayBlockingQueue.take());
        // 如果阻塞队列中的元素为空, 会调用await()方法,让线程进入等待状态
        System.out.println(arrayBlockingQueue.take());
        System.out.println("执行结束了.....");
    }
}
