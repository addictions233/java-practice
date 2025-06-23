package com.one.threadpool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: PrintNumber
 * @Description: 使用异步线程池的三种方式:
 *                1, 使用@async注解修饰方法   (异步方法) 本质上是使用AOP进行动态代理
 *                2, 使用@async注解修饰类,那么该类所用的方法都是异步方法 (异步方法)
 *                3, 使用threadPoolTaskExecutor线程池对象调用 submit()或者 execute()方法
 *                4, 使用completeFuture对象
 * @Author: one
 * @Date: 2021/05/08
 */
@Service
public class AsyncThreadPoolDemo {

    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor asyncTaskExecutor;

    /**
     * 1,第一种使用异步线程池ThreadPoolTaskExecutor的方式: 直接在异步执行的方法上添加@Async注解
     * 使用 @Async声明该方法由线程异步调用
     */
    @Async("asyncTaskExecutor")
    public void printNumber(){
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName()+"---"+i);
        }
    }

    /**
     * 方法的重载: 方法名称一定相同,方法的形参个数和类型一定要有不同
     * @param countDownLatch 主线程等待所有的异步线程执行结束后才能结束
     * 需要在子线程任务中对 countDownLatch进行计数减少操作
     */
    @Async("threadPoolTaskExecutor")
    public void printNumber(CountDownLatch countDownLatch){
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
        } finally {
            countDownLatch.countDown();
        }

    }

    /**
     * 2, 第三种使用异步线程池的方式: executor.execute()或者 executor.submit()
     * 使用线程池的调用对象来调用 execute()方法或者submit()方法
     * @param number
     */
    public void printNumber(int number) {
        /**
         * execute()方法不带返回值， 接口传入runnable对象
         */
        asyncTaskExecutor.execute( () -> {
            for (int i = 0; i < number; i++) {
                System.out.println(i);
            }
        });

        /**
         * submit()方法带返回值，接口可以传入callable对象， 也可以传入runnable对象
         */
        Future<Integer> result = asyncTaskExecutor.submit(() -> {
            int sum = 0;
            for (int i = 0; i < number; i++) {
                sum += i;
            }
            return sum;
        });
    }
}
