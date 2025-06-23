package com.one.threadmethod;

import java.util.concurrent.Callable;

/**
 * @ClassName: MyCallable
 * @Description: TODO
 * @Author: one
 * @Date: 2021/06/30
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "-------" + i);
        }
        return Thread.currentThread().getName() + "执行结束了";
    }
}
