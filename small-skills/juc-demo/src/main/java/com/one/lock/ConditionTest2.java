package com.one.lock;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author one
 * @description 使用ReentrantLock和Condition实现一个阻塞队列
 * @date 2024-12-23
 */
public class ConditionTest2 {

    public static void main(String[] args) {
        Queue queue = new Queue(5);

        Thread producer = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 每隔1s轮询生产一次
                    try {
                        Thread.sleep(1000);
                        queue.put(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread consumer = new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 每隔2s消费一次
                    try {
                        Thread.sleep(2000);
                        Object value = queue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        producer.start();
        consumer.start();
    }


    static class Queue {
        private Object[] items;

        int size = 0;

        int takeIndex;

        int putIndex;

        private ReentrantLock lock;

        private Condition notEmpty;

        private Condition notFull;

        public Queue(int capacity) {
            this.items = new Object[capacity];
            lock = new ReentrantLock();
            notEmpty = lock.newCondition();
            notFull = lock.newCondition();
        }

        public void put(Object value) {
            // 加锁
            lock.lock();
            try {
                while (size == items.length) {
                    // 当队列为满队列, 让生产者等待
                    notFull.await();
                }
                items[putIndex] = value;
                if (++putIndex == items.length) {
                    putIndex =0;
                }
                size++;
                System.out.println("Producer生产:" + value);
                // 唤醒消费者
                notEmpty.signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                // 解锁
                lock.unlock();
            }
        }

        public Object take() {
            // 加锁
            lock.lock();
            try {
                while (size == 0) {
                    // 当线程为空, 消费者进入等待状态
                    notEmpty.await();
                }
                Object value = items[takeIndex];
                if (++takeIndex == items.length) {
                    takeIndex = 0;
                }
                size--;
                // 唤醒生产者
                notFull.signal();
                System.out.println("Consumer消费:" + value);
                return value;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                // 解锁
                lock.unlock();
            }
        }
    }
}
