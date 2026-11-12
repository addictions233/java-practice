package com.one.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.alibaba.ttl.threadpool.TtlForkJoinPoolHelper;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadContextTest {

    @Test
    public void testThreadLocal() {
        // 主线程设置上下文
        ThreadLocal<String> context = new ThreadLocal<>();
        context.set("主线程上下文");
        // 异步子线程尝试获取
        new Thread(() -> {
            System.out.println(context.get()); // 输出：null（上下文丢失）
        }).start();
    }

    @Test
    public void setThreadContext() {
        ThreadLocal<String> context = new ThreadLocal<>();
        context.set("local value 技术自由圈");
        // 主线程获取上下文并传递给子线程
        String mainContext = context.get();
        new Thread(() -> {
            try {
                // 子线程设置上下文
                // 每次异步操作都要手动传递和清理，代码重复且易遗漏
                context.set(mainContext);
                System.out.println("子线程获取到的上下文：" + context.get()); // 输出：主线程上下文
            } finally {
                // 清除子线程的ThreadLocal，避免内存泄漏
                // 若忘记调用 remove()，可能导致内存泄漏，尤其在线程池场景下更危险
                context.remove();
            }
        }).start();

    }

    @Test
    public void setThreadPoolContext() {
        // 使用方式：提交任务时用装饰器包装
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ContextHolder.setContext("parent context");
        // 可统一在线程池层处理上下文传递
        executor.submit(new ContextAwareRunnable(() -> {
            System.out.println("子线程获取到的上下文：" + ContextHolder.getContext());
        }));
    }

    @Test
    public void testTtlRunnable() {
        // 定义可传递的上下文
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        // 主线程设置上下文
        context.set("主线程上下文");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(() -> {
            System.out.println("子线程获取到的上下文：" + context.get());
        });

        executor.execute(TtlRunnable.get(() -> {
            System.out.println("子线程获取到的上下文：" + context.get());
        }));
    }

    @Test
    public void testTtlThreadLocal() {
        // 定义可传递的上下文
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        // 主线程设置上下文
        context.set("主线程上下文");
        // 使用 TtlExecutors 包装线程池（关键！）
        // 创建具备“上下文传递能力”的线程池包装器
        ExecutorService executor = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(5));
        // 提交异步任务
        executor.submit(() -> {
            System.out.println("子线程获取到的上下文：" + context.get()); // 输出：主线程上下文
            context.remove(); // 清理资源
        });
    }



    @Test
    public void testTtlExecutors() {
        // 定义可传递的上下文
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        // 错误, 使用原生的ThreadLocal
//        ThreadLocal<String> context = new ThreadLocal<>();
        context.set("parent context");

        // 错误 ：直接使用原始线程池
        ExecutorService badPool = Executors.newFixedThreadPool(4);
        badPool.submit(() -> System.out.println(context.get())); // null！

        // 正确 ：用 TtlExecutors 包装
        ExecutorService goodPool = TtlExecutors.getTtlExecutorService(
                Executors.newFixedThreadPool(4)
        );
        goodPool.submit(() -> System.out.println(context.get())); // "主线程上下文"

        context.remove(); // 推荐：主动清理
    }

    @Test
    public void testInnerPool() {
        // 定义可传递的上下文
        // 要用 TransmittableThreadLocal 替代 ThreadLocal
        TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
        context.set("一级上下文");
        // 要用 TtlExecutors.getTtlExecutorService() 包装
        ExecutorService executor = TtlExecutors.getTtlExecutorService(
                Executors.newFixedThreadPool(4)
        );


        executor.submit(() -> {
            System.out.println("第一层子线程: " + context.get()); // 有值

            CompletableFuture.runAsync(() -> {
                System.out.println("第二层子线程: " + context.get()); // 依然有值
            }, executor).join();
        });

    }
}
