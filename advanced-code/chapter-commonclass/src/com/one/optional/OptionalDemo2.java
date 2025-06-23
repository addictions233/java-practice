package com.one.optional;

import java.util.Optional;

/**
 * @ClassName: OptionalDemo2
 * @Description: Optional类中的常用方法:
 *      获取Optional容器中的对象：
 *            1,T get()方法, 如果Optional对象中存储了值,就返回该值,如果没有值就抛出NoSuchElementException异常
 *            2,T orElse(T other)方法：如果有值就将其返回，否则返回指定的other对象
 *            3，T orElseGet(Supplier<? super T> supplier): 如果有值就将其返回，否则返回由Supplier接口实现提供的对象
 *            4，T orElseThrow(Supplier<? extends X> exceptionSupplier): 如果有值就将返回，否则抛出由Supplier接口实现提供的异常
 *       判断Optional容器中是否有对象的方法：
 *            1,boolean isPresent()方法 如果Optional对象中有值,就返回true, 否则返回false
 *            3,orElse() 如果Option对象有值,就返回包含的值, 如果没有就返回orElse()方法中给定的值
 * @Author: one
 * @Date: 2021/06/21
 */
public class OptionalDemo2 {
    public static void main(String[] args) {
        Optional<String> optional1 = Optional.of("zhangsan");
        // get()方法用于获取Optional容器中存储的元素, 如果容器中存储的为null, get()方法会抛出空指针异常
        String str1 = optional1.get();
        // 输出: zhangsan
        System.out.println(str1);
        // orElse()方法如果Optional容器中的元素为null，则取到orElse()方法的传参，如果元素不为null,则取元素值
        String str2 = optional1.orElse("lisi");
        // 输出: zhangsan
        System.out.println(str2);

        Optional<String> optional2 = Optional.empty();
        // 抛出异常: NoSuchElementException
//        String str2 = optional2.get();
        // orElse()方法中的返回的是Optional容器同泛型的元素对象
        System.out.println(optional2.orElse("lisi"));
        if(optional2.isPresent()) {
            System.out.println(optional2.get());
        } else {
            System.out.println("optional2是一个空对象");
        }

        // orElseGet()方法返回的是Optional容器同数据类型的元素对象，输出：zhangsan
        System.out.println(optional1.orElseGet(() -> "lisi"));
        // 输出: "lisi"
        System.out.println(optional2.orElseGet(() -> {
            Person person = new Person("lisi");
            return person.getName();
        }));
    }
}
