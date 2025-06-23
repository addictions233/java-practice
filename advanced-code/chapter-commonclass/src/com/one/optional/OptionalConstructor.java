package com.one.optional;

import java.util.Optional;

/**
 * @ClassName: OptionalConstructor
 * @Description: 构造Optional容器对象, 创建Optional对象的三种方法：
 *      1， Optional.of(T t): 创建一个装有t对象的Optional泛型容器，t必须非空， 否则会抛出空指针异常
 *      2， Optional.ofNullable(T t): 创建一个装有t对象的Optional泛型容器， t可以为null
 *      3， Optional.empty(): 创建一个空的Optional泛型容器
 * @Author: one
 * @Date: 2022/01/06
 */
public class OptionalConstructor {
    public static void main(String[] args) {
        Optional<Object> optional = Optional.empty();
        Optional<Integer> optional1 = Optional.of(1);
        Optional<Object> optional2 = Optional.ofNullable(null);
        Optional<Object> optional3 = Optional.ofNullable("bbb");
        // isPresent()方法用来判断Optional容器中是否有对象
        System.out.println(optional.isPresent()); // false
        System.out.println(optional1.isPresent()); // true
        System.out.println(optional2.isPresent()); // false

        Object aaa = optional2.orElse("aaa");
        Object aaa1 = optional3.orElse("aaa");
        System.out.println(aaa);
        System.out.println(aaa1);

    }
}
