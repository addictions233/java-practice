package com.corejava.generictype;

import java.util.ArrayList;

/**
 * 泛型方法:
 *  泛型方法在调用方法时确定具体类型
 *  泛型类在创建类的对象时确定具体类型
 *  使用 集合中 pubic <T> toArray(T[] array) 方法将集合转换为数组
 *      区别于 toArray()方法  这个方法是将集合转换为Object[] obj数组
 */
public class ArrayListDemo2 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        list.add("李四");
        list.add("王五");
        list.add("赵六");

        String[] str = new String[list.size()];
        list.toArray(str);  // 该方法直接将动态数组中的元素拷贝到数组str中
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
    }
}
