package com.one.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

public class ReactorDemo4 {

    public static void main(String[] args) throws IOException {
        // 默认的生产者和消费者在同一个线程
        Flux<Integer> flux = Flux.just(10, 20, 30).map(val -> {
            // pool-1-thread-1转换值:10  转换这里都是用的 线程池中的 线程
            System.out.println(Thread.currentThread().getName() + "转换值:" + val);
            return val;
        }).subscribeOn(
                // subscribeOn() 方法适用于订阅过程。我们可以把它放在响应链条中的任意位置
                // 放在链条任意位置效果基本一致，影响源头执行线程
                Schedulers.fromExecutor(Executors.newFixedThreadPool(1))
        ).publishOn(
                // 通过publishOn操作符指定工作线程池
                // 所有在 publishOn 操作之前的都是 main 线程执行，所有 publishOn 之后的都是 线程池线程
                Schedulers.fromExecutor(Executors.newFixedThreadPool(2))
        );

        // 直接定义数据流的 Consumer
        flux.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer val) {
                // pool-3-thread-1消费到:10 消费线程用的上面定义的线程池
                System.out.println(Thread.currentThread().getName() + "消费到:" + val);
            }
        });

        // 保持主线程不退出
        System.in.read();
    }
}
