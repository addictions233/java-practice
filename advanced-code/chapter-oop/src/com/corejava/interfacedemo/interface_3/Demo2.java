package com.corejava.interfacedemo.interface_3;

import java.util.Arrays;
import java.util.Comparator;

/**
 *  由于 Comparator比较器接口有且只有一个 compareTo抽象方法,所以可以用lambda表达式简化Comparator比较器接口使用方法
 */
public class Demo2 {
    public static void main(String[] args) {
        String[] arr = {"peter","tom","linda","爱新觉罗溥仪","威尔史密斯"};
        //调用数组工具类sort方法
    /*    Arrays.sort(arr, new Comparator<String>() {      // 匿名内部类写法
            @Override
            public int compare(String o1, String o2) {
                return o1.length()-o2.length();
            }
        });*/
//        Arrays.sort(arr,(o1,o2)-> o1.length()-o2.length());  // lambda表达式写法
        Arrays.sort(arr, Comparator.comparingInt(String::length)); //方法引用写法

        //输出打印排序后的数组
        System.out.println(Arrays.toString(arr));  // 输出: [tom, peter, linda, 威尔史密斯, 爱新觉罗溥仪]
    }
}
