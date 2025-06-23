package com.corejava.lambda.lambda;

import java.util.function.Supplier;

public interface UserHandler {

    /**
     * 在接口中使用函数式接口
     *
     * @param message 参数1
     * @param handler 函数式接口
     */
    void handleParam(String message, Handler<String, String> handler);

    /**
     * 测试函数式接口的两种传参的区别点
     *
     * @param message 参数1
     * @param supplier 函数式接口
     */
    void handleParam2(String message, Supplier<String> supplier);
}
