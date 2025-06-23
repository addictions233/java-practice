package com.corejava.method;

import java.util.ArrayList;

/**
 * @ClassName: FinalTest
 * @Description: TODO
 * @Author: one
 * @Date: 2022/04/17
 */
public class FinalTest {
    private static final Integer num;

    static {
        num = 10;
    }

    public static void main(String[] args) {
        System.out.println(num);
        ArrayList<String> list = new ArrayList<>();
        list.add("张三");
        // 测试indexof方法的使用
        System.out.println(list.indexOf("李四"));
        System.out.println(list.indexOf("张三"));
        System.out.println(list.indexOf(null));
    }
}
