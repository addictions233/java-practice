package com.one.future.completablefuture;

/**
 * @ClassName: CompletableFutureDemo4
 * @Description:  级联结果消费， 合并结果消费
 *  1,thenCompose()方法：有返回结果， 当原任务完成后，以其结果为参数，返回一个新的任务（而不是新结果，类似flatMap）
 *  2，handle()方法：有返回值 任务完成后执行BiFunction，结果转换，入参为结果或者异常，返回新结果
 *  3，whenComplete()方法：无返回值 任务完成后执行BiConsumer，结果消费，入参为结果或者异常，不返回新结果
 *  4，exceptionally()方法： 无返回值 任务异常，则执行Function，异常转换，入参为原任务的异常信息，若原任务无异常，则返回原任务结果，即不执行转换
 *  5,allOf()方法：合并多个complete为一个，等待全部完成
 *  6,anyOf()方法：合并多个complete为一个，等待其中之一完成
 *
 * @Author: one
 * @Date: 2022/01/14
 */
public class CompletableFutureTest4 {
}
