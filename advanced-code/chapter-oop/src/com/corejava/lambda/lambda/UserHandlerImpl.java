package com.corejava.lambda.lambda;

import java.util.function.Supplier;

/**
 * @author one
 * @description TODO
 * @date 2023-3-11
 */
public class UserHandlerImpl implements UserHandler{
    /**
     * 在接口中使用函数式接口
     *
     * @param message 参数1
     * @param handler 函数式接口
     */
    @Override
    public void handleParam(String message, Handler<String, String> handler) {
        System.out.println(message);
        message = message.concat("123");
        String handle = handler.handle(message);
        System.out.println(handle);
    }

    /**
     * 测试函数式接口的两种传参
     *
     * @param message
     * @param supplier
     */
    @Override
    public void handleParam2(String message, Supplier<String> supplier) {
        System.out.println(message);
        message = message.concat("123");
        String handle = supplier.get();
        System.out.println(handle);
    }
}
