package com.one.threadpool.exceptionhandle;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionTest {

    public static void main(String[] args) {
        CompletableFuture.runAsync(() -> {
            System.out.println("task execute..");
            throw new IllegalStateException();
        }).exceptionally((e) -> { // 前面抛出异常才会进入这个函数
            System.out.println(e.getMessage());
            return null;
        }).thenApply((res) -> {
            System.out.println("aaa:" + res);
            return null;
        });

        System.out.println("===================================");

        CompletableFuture.supplyAsync(() -> {
            System.out.println("task2 execute..");
            throw new IllegalStateException();
        }).handle((s, e) -> { // 异步任务不管是否发生异常，handle方法都会执行
            if (Objects.nonNull(e)) {
                System.out.println("出现异常：" + e.getMessage());
                return "unKnown";
            }
            return s;
        });
    }
}
