package com.one.future.completablefuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName: CompletableFutureDemo2
 * @Description: 单任务结果消费
 *      1， thenApply()方法：有入参，有返回结果， 将上一阶段的返回结果作为当前阶段的入参
 *      2， thenAccept()方法： 有入参，无返回结果, 消费上一阶段返回的结果
 *      3， thenReturn()方法， 无入参，无返回结果， 当上一阶段执行结束之后，执行本阶段的任务
 *         多任务结果消费
 *       1，thenCombine()方法： 有入参，有返回结果， 前两个任务的执行结果作为当前任务的入参，并返回当前任务的执行结果
 *       2，thenAcceptBoth()方法： 有入参， 无返回结果， 前两个任务的执行结果作为当前任务的入参，不返回结果
 *       3，runAfterBoth()方法： 无返回结果无入参
 * @Author: one
 * @Date: 2022/01/14
 */
public class CompletableFutureTest2 {

    public static void main(String[] args) throws IOException {
        Integer finalResult = CompletableFuture.supplyAsync(() -> {
            System.out.println("前置任务执行了...");
            return 1;
        }).thenApply((result) -> { // thenApply等待前一个任务处理结束后, 拿着前置任务的返回结果, 再进一步处理, 并返回当前结果
            System.out.println("后置任务执行了");
            return result + 2;
        }).join();
        System.out.println("最终执行结果:" + finalResult); // 输出结果: 3


        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("第二个前置任务执行了...");
            try {
                // 如果第一个任务休眠: 执行结果: 111 , 222, 333, 444
                // 如果第一个任务很快执行结束, 执行结果: 444, 333, 222, 111
                // CompletableFuture不保证thenRun后续任务的执行顺序
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        // 测试thenRun执行任务的无序性
        future.thenRun(() ->  {
            System.out.println("111");
        });

        future.thenRun(() -> {
            System.out.println("222");
        });

        future.thenRun(() ->  {
            System.out.println("333");
        });

        future.thenRun(() ->  {
            System.out.println("444");
        });

        // ForkJoin中的都是守护线程, 不能让主线程结束, 否则守护线程也销毁了
        System.in.read();
    }
}
