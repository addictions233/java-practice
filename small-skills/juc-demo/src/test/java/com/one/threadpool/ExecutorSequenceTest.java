package com.one.threadpool;

import com.one.threadpool.service.AsyncThreadPoolDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TaskExecutorSequenceTest
 * @Description: 测试使用线程池时, 如何让主线程在子线程执行结束之后再结束执行
 * @Author: one
 * @Date: 2021/05/10
 */
@SpringBootTest
public class ExecutorSequenceTest {
    @Autowired
    private AsyncThreadPoolDemo asyncThreadPoolDemo;

    /**
     *  异步线程执行是没有先后顺序, 当我们面对以下场景:
     *     主线程执行a任务, 子线程执行b任务, 我们需要在子线程执行b任务结束之后,才能让主线程执行a任务? 我们该如何解决:
     *          1, 不使用多线程, 让所有的任务都在主线程中执行,这样我们可以确定主线程中各个任务执行的先后顺序
     *          2, 将主线程中的a任务移入子线程中, 这样我们能在子线程中确定 a任务和b任务的执行顺序
     *          3, 使用 join()方法或者 CountdownLatch 来让主线程在子线程执行结束之后执行
     *   下面这种写法: 主线程会先于异步线程执行结束
     */
    @Test
    public void taskExecutorSequence(){
        System.out.println(Thread.currentThread().getName()+":主线程启动了");
        asyncThreadPoolDemo.printNumber();
        // 如果不做任何处理,主线程会比异步线程先执行结束
        System.out.println(Thread.currentThread().getName()+":主线程执行结束了");
    }

    /**
     * 第一种方式: 使用join()方法让并行的多线程变为串行的
     *  t.join()方法阻塞了调用此方法的线程(calling thread), 直到线程t线程执行结束, 此线程再继续执行
     *  通常用于main()主线程内,等待其他线程执行完成之后再结束main()主线程
     *  join()方法底层还是通过wait()方法实现的。
     */
    @Test
    public void taskExecutorSequence01() throws InterruptedException {
        System.out.println(Thread.currentThread().getName()+":主线程启动了");
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
        });
        t.start();
        // 使用 t.join()的优点是执行效率高, 缺点是必须知道线程对象, 使用线程池时在主线程中
        // 没法知道执行子线程的线程对象,也就没用使用线程对象调用join()方法
        t.join();
        System.out.println(Thread.currentThread().getName()+":主线程执行结束了");
    }

    /**
     * 第二种方式: 使用CountDownLatch
     * 单元测试中的方法必须是public修饰,无参数无返回值的方法
     * 使用 CountdownLatch对象来对线程进行计数
     * @throws InterruptedException
     */
    @Test
    public void taskExecutorSequence02() throws InterruptedException {
        // 构造函数中的整数即需要等待执行结束的子线程的个数
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        System.out.println(Thread.currentThread().getName()+":主线程启动了");
        asyncThreadPoolDemo.printNumber(countDownLatch);
        // await()方法也是阻塞,直到所有的等待线程都执行结束, await()方法和signal()方法是Conditional接口下的
        // 类似于wait()方法,但是wait()和notify()方法是Object类中的
        countDownLatch.await(60, TimeUnit.MINUTES);
        System.out.println(Thread.currentThread().getName()+":主线程执行结束了");
    }

    /**
     * 第三种方式:
     * 使用带返回结果future的异步线程,使用future.get()方法让主线程进入等待状态,等待异步线程执行结束
     */
}
