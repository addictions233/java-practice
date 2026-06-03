package com.one.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreTest1 {

    private final Semaphore semaphore = new Semaphore(3);

    private final ExecutorService executorService;

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public SemaphoreTest1(int workThreads) {
        this.executorService = Executors.newFixedThreadPool(workThreads);
    }

    public void executeTask(Runnable runnable) {
        executorService.execute(() -> {
            int taskId = taskCount.getAndIncrement();
            long startTime = System.currentTimeMillis();
            System.out.println("[" + Thread.currentThread().getName() + "] 执行任务-" + taskId + "尝试获得许可...");

            try {
                // 获取信号量
                semaphore.acquire();
                long acquireTime = System.currentTimeMillis();
                System.out.println("[" + Thread.currentThread().getName() + "] 任务-" + taskId + " 获取许可成功，等待时间: " + (acquireTime - startTime) + "ms");

                // 执行任务
                runnable.run();
                System.out.println("[" + Thread.currentThread().getName() + "] 任务-" + taskId + " 执行完成，耗时: " + (System.currentTimeMillis() - acquireTime) + "ms");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[" + Thread.currentThread().getName() + "] 任务-" + taskId + " 被中断");
            } finally {
                semaphore.release();
                System.out.println("[" + Thread.currentThread().getName() + "] 任务-" + taskId + " 释放许可");
            }
        });
    }

    /**
     * 模拟外部HTTP调用
     */
    public static void externalCall(int taskId) {
        try {
            // 模拟耗时操作（1-3秒）
            Thread.sleep(1000 + (long) (Math.random() * 2000));
            System.out.println("外部调用结果-" + taskId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public static void main(String[] args) {
        // 创建限流测试, 线程池有10个线程, 但是信号量semaphore限制最多三个线程并发调用
        SemaphoreTest1 semaphoreTest1 = new SemaphoreTest1(10);

        // 提交10个任务
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            semaphoreTest1.executeTask(() -> {
                externalCall(taskId);
            });
        }

        // 等待所有的任务执行完毕
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 关闭线程池
        semaphoreTest1.shutdown();
    }
}
