package com.one.async;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class ThreadPoolConfig {

    /**
     * 如果配置了AsyncConfigurer, @Async执行优先使用AsyncConfigurer中配置的线程池
     * @return
     */
    @Bean
    public AsyncConfigurer asyncConfigurer() {
        return new AsyncConfigurer() {
            @Override
            public Executor getAsyncExecutor() {
                return Executors.newFixedThreadPool(5);
            }

            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return new SimpleAsyncUncaughtExceptionHandler();
            }
        };
    }


    /**
     * 由于异步是用线程池来执行的, 所以开启异步必须指定线程池对象
     * @return 线程池对象
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(25);
        taskExecutor.setThreadNamePrefix("one-");
        taskExecutor.initialize();
        // 使用装饰器设计模式对异步任务进行增强
        taskExecutor.setTaskDecorator(new TaskDecorator() {
            @Override
            public Runnable decorate(Runnable runnable) {
                return new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("task execute before");
                        runnable.run();
                        System.out.println("task execute after");
                    }
                };
            }
        });
        return taskExecutor;
    }
}
