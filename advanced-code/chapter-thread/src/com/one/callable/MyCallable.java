package com.one.callable;

import java.util.concurrent.Callable;

/**
 * @ClassName: MyCallable
 * @Description: 带返回值的多线程对象的方法调用
 * @Author: one
 * @Date: 2021/06/29
 */
public class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println("向女孩表白:" + i);
        }
        return "女孩答应了";
    }
}
