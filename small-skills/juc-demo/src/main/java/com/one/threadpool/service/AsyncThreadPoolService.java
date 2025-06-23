package com.one.threadpool.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @ClassName: AsyncService
 * @Description: 3,第三种方式使用异步线程池,直接使用@Async注解修饰类,该类中所有的方法都是使用异步线程调用
 * @Author: one
 * @Date: 2021/05/08
 */
@Service
@Async("asyncTaskExecutor")
public class AsyncThreadPoolService {
    /**
     * 异步类中普通的异步线程方法
     * @param str 入参
     */
    public void say(String str) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "---" + str + "----" + i);
        }
        // 在异步线程中再创建一个线程
        Thread newThread = new Thread(() -> System.out.println(Thread.currentThread().getName() + "----" + "新线程中打印的内容"));
        newThread.start();
    }

    /**
     * 如果异步线程返回的不是Future类型,而是其他数据类型,
     * 则调用异步线程的主线程中是无法获取异步线程的执行结果的
     * 这点和同步线程是不一样的, 主线程获取到异步线程的String类型的返回结果一直为null
     * 所以异步线程的返回结果必须是Future<V>类型
     * @param num 打印次数
     * @return String
     * @throws InterruptedException
     */
    public String printNumber(int num) throws InterruptedException {  // 这中异步方法的返回值写法是错误的
        for (int i = 0; i < num; i++) {
            Thread.sleep(200);
            System.out.println(Thread.currentThread().getName() + "----" + i);
        }
        System.out.println(Thread.currentThread().getName() + "执行结束了");
        return "success";
    }

    /**
     * 定义带返回值的异步方法,即实现Callable接口,重写call()方法创建的线程对象
     * FutureTask类实现了Future接口,
     * Future接口中有 get()方法获取异步线程执行结果, isDone()方法判断异步线程是否执行结束
     *
     * @return Future<Map < String, Object>> 返回结果
     * @throws InterruptedException 异常
     */
    public Future<Map<String, Object>> executeTask() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "开始执行...");
        Map<String, Object> map = new HashMap<>(2);
        map.put("flag", true);
        Thread.sleep(200);
        map.put("name", "张三");
        map.put("age", 23);
        System.out.println(Thread.currentThread().getName() + "执行结束了...");
        return new AsyncResult<>(map);
    }

    /**
     * 定义带返回值的异步方法
     *
     * @return Future<Map < String, Object>>
     * @throws InterruptedException 异常
     */
    public Future<Map<String, Object>> executeTask2() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "开始执行...");
        Map<String, Object> map = new HashMap<>(2);
        map.put("flag", false);
        Thread.sleep(200);
        map.put("name", "李四");
        map.put("age", 24);
        System.out.println(Thread.currentThread().getName() + "执行结束了...");
        return new AsyncResult<>(map);
    }


    /**
     * 1,测试使用future.get()来让主线程在异步线程执行结束之后再结束执行
     *
     * @param name 入参
     * @return Future<String>
     * @throws InterruptedException
     */
    public Future<String> testFuture(String name) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "开始执行");
        Thread.sleep(2000);
        // 如果是带返回值的异步线程,主线程中调用get()方法能获取异步线程中抛出的异常
        int i = 1 / 0;
        System.out.println(Thread.currentThread().getName() + "执行结束..");
        return new AsyncResult<>(name + "success");
    }

    /**
     * 2,测试使用CountDownLatch类来让主线程在异步线程执行结束之后再结束执行
     * 注意: 使用CountDownLatch,异步线程如果抛出了异常终止了执行,主线程中会因为调用await()方法,一致处于等待状态
     * await()方法中可以传入参数来确定等待的最大时长
     * @param countDownLatch 异步线程计数器类
     * @throws InterruptedException
     */
    public void testCountDownLatch(CountDownLatch countDownLatch) throws InterruptedException {
        try {
            System.out.println(Thread.currentThread().getName() + "开始执行");
            Thread.sleep(2000);
//        int i  = 1 / 0;
            System.out.println(Thread.currentThread().getName() + "执行结束..");
        } finally {
            countDownLatch.countDown();
        }

    }
}
