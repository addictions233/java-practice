package com.one.optional;

import java.util.Optional;

/**
 * @ClassName: OptionalDemo3
 * @Description: ifPresent(Consumer consumer) 如果Optional对象中存在值,就执行Consumer中的方法
 *               Optional<U> map(Function<? super T, ? extends U> mapper):
 *               将Optional中的元素进行映射转变,并返回包含新的元素的Optional对象
 *               即将T泛型的Optional容器转换为U泛型的Optional容器
 * @Author: one
 * @Date: 2021/06/21
 */
public class OptionalDemo3 {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("zhangsan");
        optional.ifPresent(System.out::println);

        Person person = new Person();
        person.setName("zhangsan");
        String upName = getNameByOptional(Optional.of(person));
        System.out.println(upName);

        Person person1 = new Person();
        System.out.println(getNameByOptional(Optional.of(person1)));
    }

    /**
     * 自定义一个方法,将Person类中的name属性值转换为大写并返回
     */
    public static String getNameByOptional(Optional<Person> optional) {
        if(optional.isPresent()) {
            return optional.map(Person::getName).map(String::toUpperCase).orElse("空值");
        }
        return null;
    }
}
