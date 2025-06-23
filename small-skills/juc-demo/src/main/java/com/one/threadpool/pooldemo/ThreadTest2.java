package com.one.threadpool.pooldemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author one
 * @description 对比Thread线程频繁的创建销毁和使用ThreadPool复用线程的效率高低
 * @date 2025-3-23
 */
public class ThreadTest2 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Random random = new Random();
        List<Integer> list = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            // 评分的创建和销毁线程，是要进行系统调用，消耗资源的
            Thread thread = new Thread(() -> {
                list.add(random.nextInt());
            });
            thread.start();
            thread.join();
        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }
}
