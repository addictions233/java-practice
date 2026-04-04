package com.corejava.lambda.functionalinterface;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: ComparatorTest
 * @Description: Comparator接口: int compare(T o1, T o2);
 *      用法: 传入两个待比较的对象，如果第一个对象大于第二个对象，返回一个正数；等于的话返回0,；小于的话返回一个负数
 * @Author: one
 * @Date: 2022/03/18
 */
public class ComparatorTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 使用 Comparator 比较器进行排序
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        System.out.println(list);

        // 返回不可变集合
        List<Integer> immutableList = List.of(1, 2, 3);
    }
}
