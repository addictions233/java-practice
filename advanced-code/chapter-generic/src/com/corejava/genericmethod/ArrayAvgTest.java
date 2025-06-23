package com.corejava.genericmethod;

/**
 *  测试ArrayAvg中定义的方法
 */
public class ArrayAvgTest {
    public static void main(String[] args) {
        String avg = ArrayAvg.getAvg("mike", "jack", "rose");
        System.out.println("avg = " + avg);

        Integer avg1 = ArrayAvg.getAvg(11, 22, 33, 44, 55);
        System.out.println("avg1 = " + avg1);
    }
}
