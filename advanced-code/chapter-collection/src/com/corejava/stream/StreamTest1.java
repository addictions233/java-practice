package com.corejava.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author one
 * 练习: 定义一个集合,并添加一些数据: 1,2,3,4,5,6,7,8,9,20
 * 将集合中的奇数删除,只保留偶数
 * 遍历集合得到 : 2,4,6,8,10
 */
public class StreamTest1 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        /**
         * 匿名内部类省略了创建接口的实现类, lambda表达式省略了创建对象,方法引用省略了创建方法
         * 什么时候lambda表达式可以改写为方法引用
         *    1, lambda表达式的方法体中只做了一件事情,并且该事情为以下五种之一: 类::静态方法  类::new 类::成员方法 对象::成员方法  Arrays::new
         *    2, lambda表达式的方法的形参和返回值和方法引用的方法的形参和返回值一致
         */
        // 使用stream流对集合进行筛选,并打印
        list.stream().filter(i -> i % 2 == 0).forEach(System.out::println);
        // filter()方法负责过滤数据的  而collect()方法负责收集数据的
        /**
         * collect()方法负责收集数据,但是它不负责创建容器,也不负责把数据添加到容器中
         * Collectors.toList()方法: 在底层会创建一个List集合,并把stream流中的所有的数据都添加到list集合中
         */
        List<Integer> list1 = list.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
        System.out.println(list1);

        Set<Integer> set = list.stream().filter(i -> i % 2 == 0).collect(Collectors.toSet());
        System.out.println(set);
    }


}
