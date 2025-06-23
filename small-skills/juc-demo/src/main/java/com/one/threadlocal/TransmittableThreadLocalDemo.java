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

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + "已创建");
        }); // 先进行工作线程创建

        // 使用TTL
        final TransmittableThreadLocal<String> parent = new TransmittableThreadLocal<>();
        parent.set("value-set-in-parent");
        // 将Runnable通过TtlRunnable包装下
        executor.submit(TtlRunnable.get(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + parent.get());
        }));
    }
}
