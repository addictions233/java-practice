package com.one.threadlocal;

import org.junit.Test;

import java.util.Random;

/**
 * @author one
 * ThreadLocal使用Demo
 */
public class ThreadLocalTest {

    private final ThreadLocal<Integer> number = new ThreadLocal<Integer>() {
        /**
         * 调用ThreadLocal#get()方法如果获取的值为空
         * 就会调用#initialValue初始化方法
         */
        @Override
        protected Integer initialValue() {
            return new Random().nextInt(10);
        }
    };

    @Test
    public void testThreadLocal() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                // 没有set值, 直接调用get()方法会调用上面的#initialValue初始化方法
                Integer value = number.get();
                System.out.println("[" + Thread.currentThread().getId() + "]:" + value);
            }).start();
        }
    }
 }