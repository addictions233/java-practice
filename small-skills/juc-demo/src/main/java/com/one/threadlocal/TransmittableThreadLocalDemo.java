package com.one.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/05/31
 */
public class TransmittableThreadLocalDemo {

    static TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        // 构建线程池
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "已创建");
        }); // 先进行工作线程创建

        // 使用TTL
        parent.set("trace-123");
        // 将Runnable通过TtlRunnable包装下
        executor.submit(TtlRunnable.get(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + parent.get());
        }));

        // 再次设置, 互相不干扰
        parent.set("trace-456");
        executor.submit(TtlRunnable.get(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + parent.get());
        }));
    }
}
