package com.corejava.genericmethod;

/**
 *  ArrayAvg中定义一个getAvg(T...arr)方法
 *      目的: 来获取传入的实参组成数组的中间位置的实参值
 */
public class ArrayAvg {
    /**
     *   1, 泛型方法的定义格式
     *   2, 参数可变方法的本质是传入实参组成的参数数组, 可以直接使用数组的特点: 索引和属性length
     * @param arr
     * @param <T>
     * @return
     */
    public static <T> T getAvg(T...arr){
       return arr[arr.length/2];
    }
}
