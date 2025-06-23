package com.one.threadpool;

import com.one.threadpool.service.AsyncThreadPoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName: FutureTest
 * @Description: 测试带返回结果的异步线程 FutureTask
 * @Author: one
 * @Date: 2021/05/31
 */
@SpringBootTest
public class FutureTest {
    @Autowired
    private AsyncThreadPoolService asyncService;

    /**
     * 主线程并不会等待异步线程执行结束返回结果,所以result一直为null,与主线程和异步线程执行的先后顺序无关
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        // 异步线程的方法如果返回的数据类型不是Future<T>, 那么这种返回值将毫无意义,一直为null
        String result = asyncService.printNumber(10);
        Thread.sleep(10000);
        System.out.println(result);
        System.out.println(Thread.currentThread().getName() + "执行结束了");
    }

    /**
     * 判断使用future.get()方法获取异步线程执行结果的效率
     *
     * @throws InterruptedException
     */
    @Test
    public void futureTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Future<Map<String, Object>>> list = new ArrayList<>();
        list.add(asyncService.executeTask());
        list.add(asyncService.executeTask());
        list.add(asyncService.executeTask());
        list.add(asyncService.executeTask());
        list.add(asyncService.executeTask2());
        list.add(asyncService.executeTask2());
        list.add(asyncService.executeTask2());
        list.add(asyncService.executeTask2());
        list.add(asyncService.executeTask2());
        list.add(asyncService.executeTask2());
        list.forEach(future -> {
            Map<String, Object> map = null;
            try {
                map = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            Boolean flag = (Boolean) map.get("flag");
            System.out.println("flag:" + flag);
            String name = (String) map.get("name");
            System.out.println("name:" + name);
            Integer age = (Integer) map.get("age");
            System.out.println("age:" + age);
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 测试使用future.get()方法来让主线程在异步线程执行结束之后结束
     *
     * @throws InterruptedException
     */
    @Test
    public void testFuture() throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "开始执行了");
        Future<String> mapFuture = asyncService.testFuture("张三");
        try {
            Thread.sleep(1000);
            // 主线程中使用get方法,不仅能获取异步线程的执行结果,也能在主线程中抛出异步线程中执行的异常
            // 不建议直接使用future.get()方法, 而是使用future.get(5,TimeUnit.SECONDS)方法执行超时时间
//            mapFuture.get(5,TimeUnit.SECONDS);
            mapFuture.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行结束了");
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 测试使用CountDownLatch让主线程在异步线程执行结束之后再结束执行
     */
    @Test
    public void testCountDownLatch() throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "开始执行了");
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            asyncService.testCountDownLatch(countDownLatch);
        }
        // 调用await()方法,等待所有的异步线程执行结束, 最多等待5秒钟,然后接着执行主线程
        countDownLatch.await(5, TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + "执行结束了");
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
