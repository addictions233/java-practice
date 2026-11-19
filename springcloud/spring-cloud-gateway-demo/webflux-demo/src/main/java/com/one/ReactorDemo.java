package com.one;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public class ReactorDemo {

    public static void main(String[] args) throws IOException {
        // 定义生产者 ---> 事件(数据)
        Flux<Integer> flux = Flux.just(10, 20, 30);
        // 绑定消费者
        flux.subscribe(new Subscriber<>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                // 背压
                this.subscription = s;
                // 定义消费者能处理器的请求数量, 这里虽然写的是4, 但是生产者只定义了3个数字, 所以只能消费3个数字
                // 消费者可以在这里定义一个非常大的数字,表示可以处理生产者的所有数据
                subscription.request(4);
            }
            @Override
            public void onNext(Integer value) {
                // 消费数据
                System.out.println("消费到数据:" + value);
                if (value == 20) {
                    // 消费者取消消费
                    subscription.cancel();
                }
            }
            @Override
            public void onError(Throwable t) {

            }
            @Override
            public void onComplete() {

            }
        });

        // Mono也能充当生产者, 但是只能生产一条数据
        Mono<String> mono = Mono.just("aaa");
        mono.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }
            @Override
            public void onNext(String s) {
                System.out.println("Mono消费数据:" + s);
            }
            @Override
            public void onError(Throwable t) {

            }
            @Override
            public void onComplete() {

            }
        });

        // 不阻塞主线程会直接结束, 这里让主线程阻塞等待
        System.in.read();
    }
}
