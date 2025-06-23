package com.corejava.lambda.lambda;

/**
 * @author one
 * 函数式接口: 接口中有且只有一个用public absctract修饰的抽象方法
 */
@FunctionalInterface
public interface Handler<T, R> {
    R handle(T t);
}
