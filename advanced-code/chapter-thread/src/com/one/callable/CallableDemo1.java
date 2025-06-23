package com.one.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 第三中方式创建多线程对象: 实现Callable接口, Callable也是函数式接口, 带返回值且能抛出异常的线程对象
 * 函数式接口: @FunctionalInterface 泛型 V 表示线程执行结束后返回的数据类型
 *  public interface Callable<V> {
 *      V call()throws Exception;
 *  }
 */
public class CallableDemo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName()+": 和女孩表白"+i+"次");
                }
                return "表白成功!";
            }
        });
        // 自定义线程 myThread
        Thread myThread = new Thread(task ,"线程1");
        myThread.start();
        String s = task.get();
        System.out.println(s);
    }
}
