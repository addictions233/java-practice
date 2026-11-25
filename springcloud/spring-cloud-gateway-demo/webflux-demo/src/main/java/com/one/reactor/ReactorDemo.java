package com.one.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * Reactor Core（响应式编程核心库）
 */
public class ReactorDemo {

    public static void main(String[] args) throws IOException {
        // 定义生产者 ---> 事件(数据)
        // Publisher: 数据生产者
        Flux<Integer> flux = Flux.just(10, 20, 30);
        // 绑定消费者
        // Subscriber: 数据消费者
        // 过非阻塞的方式高效处理数据流和异步操作
        flux.subscribe(new Subscriber<>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                // 背压: 管理生产者和消费者速度不匹配的问题
                // 当生产者发送数据的速度远高于消费者处理速度时，无界缓冲会导致内存溢出（OOM）或延迟飙升
                this.subscription = s;
                // 拉取式（Pull）流量控制：订阅者通过 subscription.request(n) 显式告知发布者“我目前还能处理 n 条数据”，
                // 发布者据此控制发射节奏，实现供需平衡
                // 定义消费者能处理器的请求数量, 这里虽然写的是4, 但是生产者只定义了3个数字, 所以只能消费3个数字
                // 消费者可以在这里定义一个非常大的数字,表示可以处理生产者的所有数据
                subscription.request(4);
            }

            /**
             * 负责按需发出数据项（onNext）
             * @param value the element signaled
             */
            @Override
            public void onNext(Integer value) {
                // 消费数据
                System.out.println("消费到数据:" + value);
                if (value == 20) {
                    // 消费者取消消费
                    // 取消订阅（cancel()）
                    subscription.cancel();
                }
            }

            /**
             * 错误信号（onError）
             * @param t the throwable signaled
             */
            @Override
            public void onError(Throwable t) {

            }

            /**
             * 完成信号（onComplete）
             */
            @Override
            public void onComplete() {

            }
        });

        // Mono也能充当生产者, 但是只能生产一条数据
        // Flux代表0到N个元素的异步序列，类似于传统编程中的集合或流\
        // Mono则专为0或1个元素的场景设计，类似于Java中的Optional或Future
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
