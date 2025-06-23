package com.one.future.futuretask;

import java.util.concurrent.*;

/**
 * @author one
 */
public class FutureTaskTest {

    /**
     * 要获取异步线程的执行结果: 在java8之前是通过实现Callable接口,获取线程返回结果
     * java8之后通过CompleteFuture类型实现获取异步线程返回结果
     *
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 第一种方式, 使用线程池executor.submit(callable)获取异步线程的执行结果
        // 该方式创建的线程池可以创建无穷多个线程对象,会造成内存溢出
        ExecutorService executor = Executors.newCachedThreadPool();
        SumCallable callable = new SumCallable();
        Future<Integer> future = executor.submit(callable);
        // 通过future.get()方法获取异步线程的执行结果
        System.out.println(future.get(60,TimeUnit.SECONDS));
        // 关闭线程池
        executor.shutdown();

        //第二种方式，构建FutureTask对象, FutureTask继承了Runnable, 可以作为Thread对象的构造入参
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        // 因为FutureTask类实现了Runnable接口,所以可以作为Thread类的构造方法的参数
        new Thread(futureTask).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("主线程在执行任务");
        try {
            // FutureTask类实现了Future接口,所以可以调用get()方法阻塞主线程,获取异步线程执行结果
            System.out.println("异步线程运行结果:"+ futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("所有任务执行完毕");
    }
}

