package com.one.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

public class ReactorParallelDemo {

    public static void main(String[] args) throws IOException {
        Flux.range(1, 1000)
                .buffer(10)
                .parallel(4)
                .runOn(Schedulers.newParallel("线程池"))
                .log()
                .flatMap(Flux::fromIterable)
                .collectSortedList(Integer::compareTo)
                .subscribe(val -> System.out.println("value:" + val));

        System.in.read();
    }
}
