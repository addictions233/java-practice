package com.one.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.concurrent.Executors;

public class ReactorDemo3 {

    public static void main(String[] args) throws IOException {
        // 默认的生产者和消费者在同一个线程
        Flux<Integer> flux = Flux.just(10, 20, 30).map(val -> {
            // main转换值:10  转换这里都是用的 main 线程
            System.out.println(Thread.currentThread().getName() + "转换值:" + val);
            return val;
        }).publishOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(1)));

        flux.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(3);
            }

            @Override
            public void onNext(Integer val) {
                // pool-1-thread-1消费到:10 消费线程用的上面定义的线程池
                System.out.println(Thread.currentThread().getName() + "消费到:" + val);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        System.in.read();
    }
}
