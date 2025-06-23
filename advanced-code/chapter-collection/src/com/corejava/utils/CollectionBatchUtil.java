package com.corejava.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @ClassName: CollectionUtil
 * @Description: 集合工具类: 提供对集合进行分批处理
 * @Author: one
 * @Date: 2021/12/23
 */
public class CollectionBatchUtil {
    /**
     * 对集合进行分批处理
     *      分批次数:   集合 size   每批 batchSize
     *      需要处理的次数:  (size + batchSize - 1) / batchSize
     *
     * @param list 集合对象
     * @param batchSize 每次处理最大数量
     * @param consumer 处理方法
     * @param <E> 集合数据类型
     */
    public static <E> void collectionBatchHandler(List<E> list, int batchSize, Consumer<List<E>> consumer) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if (batchSize < 0) {
            throw new IllegalArgumentException("分批处理的参数不能小于0");
        }
        List<List<E>> subList = ListUtils.partition(list, batchSize);
        subList.forEach(consumer);
    }

    public static void main(String[] args) throws InterruptedException {
        // 测试对集合进行分批处理
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        collectionBatchHandler(integerList, 6, (list) -> {
            list.forEach(i -> {
                System.out.println(i * 2);
            });
        });

        // 测试对集合进行并行处理
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        collectionParallelHandler(integerList,6,(list) -> {
            list.forEach(i -> {
                System.out.println( "a" + i);
            });
        },executorService);
        executorService.shutdown();
    }

    /**
     * 使用多线程对数据进行并发处理
     *
     * @param list list
     * @param consumer consumer
     * @param <E> 泛型
     */
    public static <E> void collectionParallelHandler(List<E> list, int batchSize, Consumer<List<E>> consumer, ExecutorService executorService) throws InterruptedException {
        // 创建固定长度的线程数量
        List<List<E>> subList = Lists.partition(list, batchSize);
        CountDownLatch countDownLatch = new CountDownLatch(subList.size());
        subList.forEach(eachList -> {
            executorService.execute(() -> {
                        try {
                            consumer.accept(eachList);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }

        );
        countDownLatch.await();
    }

}
