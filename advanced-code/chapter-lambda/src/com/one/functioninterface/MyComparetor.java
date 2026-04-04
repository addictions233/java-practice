package com.one.functioninterface;

/**
 * 函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
 * 函数式接口可以被隐式转换为 lambda 表达式。
 * Lambda 表达式和方法引用（实际上也可认为是Lambda表达式）上。
 * @param <T> 泛型
 */
@FunctionalInterface
public interface MyComparetor<T> {

    /**
     * 函数式接口有且只有一个抽象方法
     * @param t1 t1
     * @param t2 t2
     * @return 比较结果
     */
    int compareTo(T t1, T t2);
}
