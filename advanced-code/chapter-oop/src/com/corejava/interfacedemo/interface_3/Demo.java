package com.corejava.interfacedemo.interface_3;

import java.util.Arrays;

/**
 *  使用字符串长度比较器类字符串长度数组进行排序
 */
public class Demo {
    public static void main(String[] args) {
        String[] arr = new String[]{"a","ccc","eeeee","dddd","bb"};
        //调用数组工具类中Arrays类中的sort()方法对字符串数组进行排序
        Arrays.sort(arr,new LengthComparator());
        System.out.println(Arrays.toString(arr));  // 输出: [a, bb, ccc, dddd, eeeee]
    }
}
