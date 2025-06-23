package com.one.future.completablefuture;

/**
 * @ClassName: CompletableFuture3
 * @Description: 任意结果消费，
 *  1，applyToEither()方法：有返回结果，其中任一任务完成后，执行Function，结果转换，入参为已完成的任务结果。返回新结果，要求两个任务结果为同一类型
 *  2，acceptEither()方法：无返回结果，其中任一任务完成后，执行Consumer，消费结果，入参为已完成的任务结果。不返回新结果，要求两个任务结果为同一类型
 *  3，runAfterEither()方法：无返回结果无入参，其中任一任务完成后，执行Runnable，消费结果，无入参。不返回新结果，不要求两个任务结果为同一类型
 * @Author: one
 * @Date: 2022/01/14
 */
public class CompletableFutureTest3 {
}
