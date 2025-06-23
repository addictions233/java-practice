package com.one.threadpool.pooldemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author one
 * @description TODO
 * @date 2025-3-23
 */
public class ThreadPoolTest2 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Random random = new Random();
        // 线程池会复用线程，所以比较节省资源
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Integer> list = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            executorService.execute(() -> {
                list.add(random.nextInt());
            });
        }
        // 调用shutdown方法之后还是会执行完所有的任务，但是不会接受新的任务
        executorService.shutdown();
        // 线程池阻塞直到所有的任务都完成
        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }
}
