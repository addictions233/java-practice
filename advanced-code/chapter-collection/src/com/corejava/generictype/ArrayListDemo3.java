package com.corejava.generictype;

import java.util.ArrayList;

public class ArrayListDemo3 {
    public static void main(String[] args) {
        ArrayList<String> strings = addElements(new ArrayList<String>(), "张三", "李四", "王五");
        for (String string : strings) {
            System.out.println(string);
        }

    }

    /**
     * 定义一个可变参数的方法, 方法参数和方法返回值都带泛型的泛型方法
     * 可变参数的本质,就是将多个参数封装在一个数组中
     * @param list 动态数组
     * @param elements 元素
     * @param <T> 泛型方法
     * @return ArrayList<T> 动态数组
     */
    public static <T> ArrayList<T> addElements(ArrayList<T> list,T...elements){
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }
}
