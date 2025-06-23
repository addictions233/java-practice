package com.one.threadmethod;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: Main
 * @Description: TODO
 * @Author: one
 * @Date: 2021/06/30
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable callable1 = new MyCallable();
        // FutureTask类即作为了获取多线程执行结果对象,也作为传递线程任务的中间对象
        FutureTask<String> futureTask1 = new FutureTask<>(callable1);
        Thread thread1 = new Thread(futureTask1);
        thread1.setName("飞机");
        thread1.setPriority(1);

//        MyCallable callable2 = new MyCallable();
        // 同一线程任务Callable对象可以交给多个线程对象Thread来执行,每个FutureTask获取自己封装的线程任务的执行结果
        FutureTask<String> futureTask2 = new FutureTask<>(callable1);
        Thread thread2 = new Thread(futureTask2);
        thread2.setName("坦克");
        thread2.setPriority(10);

        //开启线程
        thread1.start();
        thread2.start();

        //获取多线程执行的结果
        String result1 = futureTask1.get();
        System.out.println("FutureTask1的执行结果:" + result1);
        String result2 = futureTask2.get();
        System.out.println("FutureTask2的执行结果:" + result2);

    }
}
