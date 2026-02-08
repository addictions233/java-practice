package com.one.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async(value = "taskExecutor") // 可以手动指定执行的线程池
    public void asyncExec() {
        System.out.println("threadName:" + Thread.currentThread().getName());
        System.out.println("aaa");
    }
}
