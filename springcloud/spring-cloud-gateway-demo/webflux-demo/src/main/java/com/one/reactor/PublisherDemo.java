package com.one.reactor;

import java.io.IOException;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Reactive Streams是JVM面向流的库的标准和规范，是 jdk9 中的 API，方便本地开发基于异步、消息驱动的全事件回调系统：响应式系统。
 * API Components ：注意，使用这些组件是天然异步且由 ForkJoinPool 线程池启用执行，所以程序最后调用System.in.read()来控制主线程不完全结束
 * Publisher ：发布者接口；产生数据流
 * Subscriber ：订阅者接口； 消费数据流
 * Subscription ：订阅关系；订阅关系是发布者和订阅者之间的关键接口。订阅者通过订阅来表示对发布者产生的数据的兴趣。
 *                 订阅者可以请求一定数量的元素，也可以取消订阅
 * Processor ：处理器是同时实现了发布者和订阅者接口的组件。它可以接收来自一个发布者的数据，进行处理，并将结果发布给下一个订阅者。
 *             处理器在Reactor中充当中间环节，代表一个处理阶段，允许在数据流中进行转换、过滤和其他操作
 */
public class PublisherDemo {

    /**
     * 数据流向：Publisher （ dataBuffer） -> N 个 Processor -> Subscriber
     * @param args 参数
     */
    public static void main(String[] args) throws IOException {
        // 1. 定义发布者, 可以用来发布消息
        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {
            // 2. 定义订阅者, 订阅者可以用来订阅发布者发布的消息
            Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {
                // 保存发布者和订阅者的绑定关系
                private Flow.Subscription subscription;
                private AtomicInteger count = new AtomicInteger(0);
                @Override
                public void onSubscribe(Flow.Subscription subscription) {
                    this.subscription = subscription;
                    System.out.println("订阅事件发生了:" + Thread.currentThread().getName());
                    // 背压模式，订阅者向发布者请求发布信息
                    subscription.request(1);
                }
                @Override
                public void onNext(String item) {
                    count.getAndIncrement();
                    System.out.println("订阅数据:" + item + ",订阅的线程:" + Thread.currentThread().getName());
                    if (count.get() < 3) {
                        // 继续订阅数据
                        subscription.request(1);
                    } else {
                        // 取消订阅
                        subscription.cancel();
                    }
                }
                @Override
                public void onError(Throwable throwable) {
                    System.out.println("订阅发生了异常:" + throwable);
                }
                @Override
                public void onComplete() {
                    System.out.println("订阅执行结束了");
                }
            };

            // 3. 发布者绑定订阅者
            publisher.subscribe(subscriber);

            // 4.发布者发布消息
            for (int i = 1; i < 5; i++) {
                publisher.submit("order" + i);
            }

            // 这样才能触发 onComplete 回调
//            publisher.close();

        }

        System.in.read();
    }
}
