package com.corejava.lambda.functionalinterface;

import java.util.Random;
import java.util.function.Supplier;

/**
 * @ClassName: SupplierTest
 * @Description: Supplier接口:  T get(); Supplier当做生产者 ，不接受参数，最后给我们返回一个值
 *      用法: 不需要参数，无偿提供一个对象, 用于一些算法容器的初始化，
 *            如Collector接口，用来提供流中数据的初始存储容器
 * @Author: one
 * @Date: 2022/03/18
 */
public class SupplierTest {
    public static void main(String[] args) {
        Integer luckNumber = luckNumber(() -> {
            Random random = new Random();
            // 产出 [0,10)之间的随机数
            return random.nextInt(10);
        });
        System.out.println(luckNumber);
    }

    private static Integer luckNumber(Supplier<Integer> supplier) {
        return supplier.get();
    }
}
