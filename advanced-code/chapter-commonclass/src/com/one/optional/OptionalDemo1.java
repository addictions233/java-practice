package com.one.optional;

import java.util.Optional;

/**
 * @ClassName: OptionalDemo1
 * @Description: Optional类主要是解决空指针异常
 *  Optional是一个泛型容器， 该容器可以保存一个对象（包括null）, 如果对象存在，则isPresent()方法返回true,
 *  如果没有保存对象或者保存对象为null, 则isPresent()方法返回false,
 *  注意： of()方法，如果参数为null, 会抛出空指针异常， 而ofNullable()方法允许参数为null
 *        判断Optional容器中是否包含对象：
 *          1，boolean isPresent(): 如果Optional容器中包含对象返回true, 否则返回false
 *          2，void ifPresent(Consumer<? supper T> consumer): 如果Optional容器中有值，就执行Consumer接口中实现的方法
 * @Author: one
 * @Date: 2021/06/21
 */
public class OptionalDemo1 {
    public static void main(String[] args) {
        //Optional类的创建方式
        // 1,使用 of()静态方法创建Optional对象
        Optional<String> optional1 = Optional.of("zhangsan");
        // 如果传入的参数为null,会报空指针异常, of()方法的形参不能为null
//        Optional<Object> optional2 = Optional.of(null);

        // 2, 使用ofNullable()方法创建Optional对象， ofNullable()可以使用null作为形参创建Optional对象
        Optional<Object> optional3 = Optional.ofNullable(null);
        // ifPresent()方法只有当Optional容器中的对象不为空的时候才会执行Consumer接口中实现的方法
        optional3.ifPresent(System.out::println);
        Object obj = optional3.orElse("lisi");
        System.out.println(obj);

        //3,使用empty()方法创建Optional对象, 直接创建空的Optional对象
        Optional<Object> optionalEmpty = Optional.empty();
        System.out.println(optionalEmpty.isPresent());

        // 4, ifPresent(Consumer consumer)方法
        Optional<String> optional4 = Optional.ofNullable("wangwu");
        optional4.ifPresent((str) -> {
            System.out.println(str.toUpperCase());
        });
    }
}
