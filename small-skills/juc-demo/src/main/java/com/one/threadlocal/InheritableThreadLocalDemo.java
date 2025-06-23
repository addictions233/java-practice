package com.one.threadlocal;

import javax.lang.model.element.VariableElement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/05/31
 */
public class InheritableThreadLocalDemo {

    static ThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    static Executor executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        // 在主线程中设置初始化值
        threadLocal.set("one");
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName());
        // 在main主线程中创建了异步线程, 所以异步线程的parent线程就是主线程
        // 所以可以在异步线程中获取主线程设置的InheritableThreadLocal属性执行
        executor.execute(() ->  {
            // 在异步线程中获取主线程设置的初始化值
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + ":" + threadLocal.get());
            // 对初始化值进行修改
            threadLocal.set("two");
            System.out.println(thread.getName()+":" + threadLocal.get());
        });
    }
}
