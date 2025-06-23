package com.corejava.lambda.functionalinterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @ClassName: PredicateTest
 * @Description: Predicate接口: boolean test(T t); 该抽象方法是用来传入一个值，返回一个boolean值
 *      用法: 传如一个对象，返回一个布尔值,多用于过滤数据
 * @Author: one
 * @Date: 2022/03/18
 */
public class PredicateTest {
    private static List<String> targetList;

    static {
        targetList = new ArrayList<>();
        targetList.add("abc");
        targetList.add("bcd");
        targetList.add("def");
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("abc", "dsfsd", "def", "dsk");
        for (String str : list) {
            if(method(str,(string) -> targetList.contains(string))) {
                System.out.println(str);
            }
        }
    }

    private static boolean method(String str, Predicate<String> predicate) {
        return predicate.test(str);
    }

}
