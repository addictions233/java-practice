package com.one.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: Main
 * @Description: FutureTask<V>类实现了两个重要接口:
 *                  Runnable接口中有 run()方法, Future接口中有 isDone() get()等方法
 *              FutureTask类的对象的两个作用:
 *                  1, 调用get()方法用来获取线程的执行结果
 *                  2, 作为中间对象将Callable接口中的线程任务传递给Thread线程对象
 * @Author: one
 * @Date: 2021/06/29
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> myCallable = new MyCallable();
        // FutureTask类继承了Runnable接口和Future接口
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        // 所有的多线程对象都是Thread派生类的对象, FutureTask只是任务对象
        Thread thread = new Thread(futureTask);
        thread.start();
        // get()方法获取异步线程执行的结果,如果异步线程没有执行结束,那么get()方法会一致阻塞
        // 直到异步线程执行结束 所以get()方法不能写在 start()方法前面,这样get()方法会一直阻塞
        String result = futureTask.get();
        System.out.println(result);
    }
}
