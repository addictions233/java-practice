package com.one.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ReactorDemo3 {

    public static void main(String[] args) throws IOException {
        // 默认的生产者和消费者在同一个线程
        Flux<Integer> flux = Flux.just(10, 20, 30).map(val -> {
            // main转换值:10  转换这里都是用的 main 线程
            System.out.println(Thread.currentThread().getName() + "转换值:" + val);
            return val;
        }).publishOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(1))); // 线程调度器（Scheduler）



        // 直接定义 消费数据流的 Consumer
        flux.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer val) {
                // pool-1-thread-1消费到:10 消费线程用的上面定义的线程池
                System.out.println(Thread.currentThread().getName() + "消费到:" + val);
            }
        });

        // 保持主线程不退出
        System.in.read();
    }
}
