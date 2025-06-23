package com.one.future.completablefuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author one
 * @description 测试CompletableFuture的使用
 * @date 2025-1-13
 */
public class CompletableFutureDemo04 {

    public static void main(String[] args) {
        String twoResult = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行第一个任务..");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "执行第一个任务结束";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "执行第二个任务..");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "执行第二个任务结束";
        }), (result1, result2) -> {
            System.out.println("获得两个任务结果");
            return result1 + result2;
        }).join();
        System.out.println(twoResult);
    }
}
