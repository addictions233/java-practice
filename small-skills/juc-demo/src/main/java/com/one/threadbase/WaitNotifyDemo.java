package com.one.threadbase;

public class WaitNotifyDemo {

    private static Object lock = new Object();
    private static  boolean flag = true;

    /**
     * 使用synchronized锁的等待唤醒机制的标准写法
     * @param args
     */
    public static void main(String[] args) {
        new Thread(() ->  {
            // 1. 对代码块加synchronized锁
            synchronized (lock) {
                // 2. 对判断标识符进行while死循环
                while (flag) {
                    try {
                        // 3. 条件不满足, 线程进入waiting等待状态
                        System.out.println(Thread.currentThread().getName() + " enter into waiting");
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 4. 被唤醒之后抢占synchronized锁, 获取到锁就继续执行业务代码
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " wait end, execute task...");
            }
        }).start();

        new Thread(() ->  {
            // 1. 对代码块加synchronized锁
            synchronized (lock) {
                // 2. 对判断标识符进行while死循环
                while (flag) {
                    try {
                        // 3. 条件不满足, 线程进入waiting等待状态
                        System.out.println(Thread.currentThread().getName() + " enter into waiting");
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                // 4. 被唤醒之后抢占synchronized锁, 获取到锁就继续执行业务代码
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " wait end, execute task...");
            }
        }).start();

        new Thread(() -> {
            // 5. 对代码块加synchronized锁
            synchronized (lock) {
                if (flag) {
                    // 6. 执行业务代理
                    System.out.println(Thread.currentThread().getName() + " notifier execute task...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 7.任务执行结束修改标识
                    System.out.println(Thread.currentThread().getName() +  " notifier execute end...");
                    flag = Boolean.FALSE;
                    // 8. 通知唤醒等待状态线程
                    lock.notifyAll();
                }
            }
        }).start();
    }
}