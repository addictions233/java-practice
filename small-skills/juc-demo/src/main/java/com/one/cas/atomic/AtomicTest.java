package com.one.cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author one
 * @description 原子类保证了get 和 add操作的原子性(一个或者多个操作要么同时成功, 要么同时失败): 自旋有空转的问题
 * @date 2024-4-10
 */
public class AtomicTest {

    public static void main(String[] args) {
        AtomicInteger value = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            new Thread(() ->  {
                for (int j = 0; j < 10000; j++) {
                    value.getAndIncrement();
                }
            }).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(value.get());
    }
}
