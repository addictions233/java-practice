package com.one.future.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: Task02
 * @Description: 创建多线程的三种方式:
 * 1, 继承Thread类
 * 2,实现Runnable接口,重写run()方法
 *      如果你自己通过继承Thread类或者实现Runnable接口的方式去创建线程,是无法获取线程方法的返回值的
 * 3,实现Callable接口和FutureTask类,重写call()方法
 *      使用Callable接口重写的call()方法可以在主线程中获取异步线程的方法返回值并且可以在主线程中获取到异步线程抛出的异常
 * @Author: one
 * @Date: 2021/05/10
 */
public class SumCallable implements Callable<Integer> {
    /**
     * Callable定义需要执行的任务
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for (int i = 1; i <= 99; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new SumCallable());
        new Thread(futureTask).start();
        Integer result = futureTask.get(); // 阻塞执行
        System.out.println("执行结果:" + result);
    }
}
