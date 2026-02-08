package com.one.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void asyncExec() {
        System.out.println("threadName:" + Thread.currentThread().getName());
        System.out.println("aaa");
    }
}
