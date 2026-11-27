package com.one.reactor;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class FluxDemo {

    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(10, 20, 30, 40);
        flux.subscribe(System.out::println);
        flux.subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("值:" + value + ",线程名称:" + Thread.currentThread().getName());
            }
        });
    }
}
