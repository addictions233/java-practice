package com.one.packageclass;
/**
 *  将字符串 "38 33 61 29 73"中的数字提取出来
 *    方法:
 *      1, String[] split(想要的切割方法)  将字符串按照正则表达式切割,并将切割后的字符串存储到数组中返回
 *      2, int parse(String string) 将字符串的字面值转换为基本数据类型数
 */

import java.util.Arrays;

public class PackageClassTest1 {
    public static void main(String[] args) {
        String str = "38 33 61 29 73";
        String[] arr = str.split(" ");
        int[] arr2 = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = Integer.parseInt(arr[i]);
        }
        System.out.println(Arrays.toString(arr2));
    }
}
