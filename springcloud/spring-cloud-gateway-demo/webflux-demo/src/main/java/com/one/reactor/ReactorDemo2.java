package com.one.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.io.IOException;

public class ReactorDemo2 {

    public static void main(String[] args) throws IOException {
        Flux<Long> flux = Flux.defer(() -> Flux.just(100L));
        // 对消费流中的数据进行处理
        Flux<Long> mapFlux = flux.map(val -> val + 1000);
        // 对每个数据进行遍历操作
        Flux<Long> eachFlux = mapFlux.doOnEach(sig -> System.out.println(sig.get()));
        eachFlux.subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1000);
            }

            @Override
            public void onNext(Long val) {
                System.out.println("消费到的值:" + val);
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
