package com.one.cas.atomic;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @description: 解决CAS自旋的ABA问题需要借助版本号, 有修改版本号 +1, 这样每次修改都留痕了
 * @author: wanjunjie
 * @date: 2024/04/10
 */
public class ABATest {

    public static void main(String[] args) {
        AtomicInteger value = new AtomicInteger();
        AtomicMarkableReference<Integer> markableReference = new AtomicMarkableReference<>(0, true);
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(0,0);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            new Thread(() ->  {
                if (random.nextInt(10) % 2 == 0) {
//                    boolean flag = value.compareAndSet(0, 1);
                    // 参数: 期望值, 新值, 希望的版本号, 新的版本号    (加上版本号用来解决ABA的问题)
                    boolean flag = stampedReference.compareAndSet(0, 1, 0, 1);
                    System.out.println(Thread.currentThread().getName() + "线程修改结果:" + flag + ",结果从0改为1");
                } else {
//                    boolean flag = value.compareAndSet(1, 0);
                    boolean flag = stampedReference.compareAndSet(1,0,1,2);
                    System.out.println(Thread.currentThread().getName() + "线程修改结果:" + flag + ",结果从1改为0");
                }

            },"thread" + i).start();
        }
    }
}
