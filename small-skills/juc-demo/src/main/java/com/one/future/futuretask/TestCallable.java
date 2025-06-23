package com.one.future.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask类继续了Future接口,可以在构造方法中传入Callable线程任务对象,具有get()方法获取线程执行结果
 * 使用带返回值的 Future对象来让主线程在子线程执行结束之后执行: 原理是依赖 future.get()方法来阻塞主线程
 * @author  one
 */
public class TestCallable implements Callable<String> {

    private String threadName;

    /**
     * 重写构造方法
     * @param threadName 线程名称
     */
    public TestCallable(String threadName) {
        this.threadName = threadName;
    }

    /**
     * 重写call()方法
     * @return 异步线程执行的返回结果
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        return "线程" + threadName + "执行结束了";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("主线程开始等待子线程执行完成");
        // 注意区分: FutureTask类实现了Future接口,同时实现Runnable接口
        FutureTask<String> taskA = new FutureTask<>(new TestCallable("A"));
        FutureTask<String> taskB = new FutureTask<>(new TestCallable("B"));
        // 创建线程对象,执行线程任务, 因为FutureTask实现了Runnable接口,可以作为构造方法传参
        // 开启线程,让线程进入就绪状态
        new Thread(taskA).start();
        new Thread(taskB).start();
        //判断子线程A是否执行结束, 可以用 isDone()方法来轮询异步线程的执行结果，直到异步线程执行结束
        while (!taskA.isDone()) {
            System.out.println("线程A未执行完，主线程继续等待");
        }
        //判断子线程B是否执行结束
        while (!taskB.isDone()) {
            System.out.println("线程B未执行完，主线程继续等待");
        }
        //Future就是对于具体的Runnable或者Callable线程任务的执行结果进行取消、查询是否完成、获取结果等操作的对象。
        //这里使用 FutureTask类中的get()方法阻塞主线程
        //必要时可以通过get方法获取执行结果，该方法会阻塞直到多线程执行结束并返回结果。
        //打印一下线程A的返回值
        System.out.println(taskA.get());
        //打印一下线程B的返回值
        System.out.println(taskB.get());
        System.out.println("子线程执行完成了，主线程开始执行了");
    }
}
