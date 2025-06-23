package com.corejava.stream;

import java.util.Arrays;
import java.util.List;

/**
 *  统计一个字符串中单词长度大于等于5的单词个数
 *      1,常规实现方法
 *      2.Stream流实现方法
 */
public class StreamDemo1 {
    public static void main(String[] args) {
        String str = "java is a difficult programming language";
        /*
            Arrays工具类中:
            static <T> List<T> asList​(T... a) 返回由指定数组支持的固定大小的列表
         */
        List<String> strings = Arrays.asList(str.split(" "));

        //遍历集合,统计长度大于5的单词个数
        int count = 0;
        for (String string : strings) {
            if(string.length()>=5){
                count++;
            }
        }
        System.out.println("count = " + count); // count = 3

        //用stream流实现该功能
        long count1 = strings.stream().filter(str1->str1.length()>5).count();
        System.out.println("count1 = " + count1); // count1 = 3

        //用parallelStream实现该功能
        long count2 = strings.parallelStream().filter(str2 -> str2.length() > 5).count();
        System.out.println("count2 = " + count2);
    }
}
