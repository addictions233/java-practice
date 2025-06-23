package com.one.future.completablefuture;

import com.one.threadpool.entity.UserInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: CompletableFutureDemo
 * @Description: CompletableFuture的创建方式：
 *          1， 使用默认线程池：  2, 使用自定义线程池
 * @Author: one
 * @Date: 2022/01/14
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 1，使用默认线程池
         * 默认使用 ForkJoinPool.commonPool()，commonPool是一个会被很多任务 共享 的线程池，
         * 比如同一 JVM 上的所有 CompletableFuture、并行 Stream 都将共享 commonPool，
         * commonPool 设计时的目标场景是运行 非阻塞的 CPU 密集型任务，为最大化利用 CPU，
         * 其线程数默认为 CPU 数量 - 1。
         */
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        /**
         * 2, CompletableFuture.runAsync(runnable,executor):
         *      如果不传入executor线程池对象,就是使用commonPool线程池
         *      如果传入自定义的executor线程池 ,就自定义的executor线程池
          * runAsync()方法： 使用异步线程进行处理,不获取异常方法的返回结果
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        // 这里使用自定义的executor线程池对象,runAsync()方法不会返回异常方法的执行结果,就没有必要接收
        CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName()+ "输出:hello world");
        }, executor);
        // 关闭线程池
        executor.shutdown();

        /**
         * 3,supplyAsync()方法： 使用异步线程进行处理,获取异步方法的返回结果
         */
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "输出: hello java");
            // 休眠3s
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        // get()方法会阻塞异步线程，直到异步线程执行结束返回结果, 会抛出很多异常
//        String result = completableFuture2.get();
        // join方法同样可以获取异步线程的处理结果, 但是不会抛出异常, 通常用join()方法
        String result = completableFuture2.join();
        System.out.println(result);

    }

    /**
     * 伪代码: 使用CompletableFuture获取多个异步线程方法的执行结果, 此外，Fork/join框架也提供了执行任务并返回结果的能力
     *
     * @param id
     */
    public UserInfo getUserInfo(Long id) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        final UserInfo userInfo = new UserInfo();
        CompletableFuture userFuture = CompletableFuture.supplyAsync(() -> {
            getRemoteUserAndFill(id, userInfo);
            return Boolean.TRUE;
        }, executor);

        CompletableFuture bonusFuture = CompletableFuture.supplyAsync(() -> {
            getRemoteBonusAndFill(id, userInfo);
            return Boolean.TRUE;
        }, executor);

        CompletableFuture growthFuture = CompletableFuture.supplyAsync(() -> {
            getRemoteGrowthAndFill(id, userInfo);
            return Boolean.TRUE;
        }, executor);
        // join()方法使该方法所在的主线程阻塞,而调用该方法的异步线程必须先执行结束
        CompletableFuture.allOf(userFuture, bonusFuture, growthFuture).join();

        userFuture.get();
        bonusFuture.get();
        growthFuture.get();

        return userInfo;
    }

    private void getRemoteGrowthAndFill(Long id, UserInfo userInfo) {
    }

    private void getRemoteBonusAndFill(Long id, UserInfo userInfo) {
    }

    private void getRemoteUserAndFill(Long id, UserInfo userInfo) {
    }

}
