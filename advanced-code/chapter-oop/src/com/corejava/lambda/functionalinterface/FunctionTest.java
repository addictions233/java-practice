package com.corejava.lambda.functionalinterface;

import java.util.function.Function;

/**
 * @ClassName: FunctionTest
 * @Description: Function接口:  R apply(T t); 该方法也是申明该函数具体执行什么样的操作
 *      用法：用户对数据的转化，将一个数据通过一个算法转为另外一个对象
 * @Author: one
 * @Date: 2022/03/18
 */
public class FunctionTest {
    public static void main(String[] args) {
        // 求一个数的平方
        System.out.println(method(3, (a) -> a*a));

        // 求一个数乘以2
        System.out.println(method(3, (a) -> a*2));
    }

    /**
     * 给定一个参数num, 在function接口中定义对num的处理方式,并将处理结果返回
     * @param num 参数
     * @param function function接口
     * @return 处理结果
     */
    private static Integer method(Integer num,Function<Integer, Integer> function) {
        // 调用Function接口的apply()方法
        return function.apply(num);
    }
}
