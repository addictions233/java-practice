package com.one.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

/**
 * 默认订阅者都是使用当前线程执行，而发布者默认与订阅者使用同一线程，可使用 publishOn 和 subscribeOn 指定对应 Scheduler
 *
 * publishOn ：指定后续执行的线程，影响调用位置起到后续的线程
 * subscribeOn ：指定源到后续的所有线程，但不会影响 publishOn 指定的逻辑
 */
public class ReactorThreadDemo {
    public static void main(String[] args) throws IOException {
        Scheduler publisher = Schedulers.newParallel("Publisher");
        Scheduler subscriber = Schedulers.newParallel("Subscriber");

        Flux.range(1, 3)
                .doOnNext(item -> System.out.println("item1:" + item + ",线程:" + Thread.currentThread().getName()))
                .publishOn(publisher)
                .doOnNext(item -> System.out.println("item2:" + item + ",线程:" + Thread.currentThread().getName()))
                .subscribeOn(subscriber)
                .doOnNext(item -> System.out.println("item3:" + item + ",线程:" + Thread.currentThread().getName()))
                .subscribe(item -> System.out.println("item4:" + item + ",线程:" + Thread.currentThread().getName()));

        System.in.read();
    }
}
