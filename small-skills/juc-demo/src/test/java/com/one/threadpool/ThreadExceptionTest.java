package com.one.threadpool;

import com.one.threadpool.service.AsyncThreadPoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: ThreadTest3
 * @Description: 如何在主线程中捕获异常线程抛出的异常,并进行处理
 * @Author: one
 * @Date: 2021/07/27
 */
@SpringBootTest
public class ThreadExceptionTest {

    @Autowired
    private AsyncThreadPoolService asyncThreadPoolService;


    @Test
    public void test() {
        System.out.println(Thread.currentThread().getName() + "开始执行了....");
        try {
            Future<String> future = asyncThreadPoolService.testFuture("测试异步线程");
            String result = future.get(5, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("异步线程执行出现异常:" + e);
        }
        System.out.println(Thread.currentThread().getName() + "执行结束了....");
    }
}
