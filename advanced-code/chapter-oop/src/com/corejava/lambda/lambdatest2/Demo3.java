package com.corejava.lambda.lambdatest2;


public class Demo3 {
    public static void main(String[] args) {
        // 使用lambda表达式替换函数式接口的实现类对象
        useInterA((num) -> num + "个hello", 10);

    }

    public static void useInterA(InterC a, int num) {
        String str = a.concat(num);
        System.out.println("str=" + str);
    }
}
