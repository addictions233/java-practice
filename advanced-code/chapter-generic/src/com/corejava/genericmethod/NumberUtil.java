package com.corejava.genericmethod;

public class NumberUtil {

    /**
     * 引入泛型是为了实现多种数据类型对一段代码的复用
     */
    private static <T extends Number> double add2(T a, T b) {
        System.out.println(a + "+" + b + "=" + (a.doubleValue() + b.doubleValue()));
        return a.doubleValue() + b.doubleValue();
    }

    /**
     * 如果我们没有泛型, 实现不同类型的数据相加, 就要创建多种方法
     */
    private static int add(int a, int b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    private static float add(float a, float b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    private static double add(double a, double b) {
        System.out.println(a + "+" + b + "=" + (a + b));
        return a + b;
    }

    public static void main(String[] args) {
        // 没有泛型
        System.out.println(NumberUtil.add(10, 20));
        System.out.println(NumberUtil.add(2.0, 3.0));

        // 引入泛型
        System.out.println(NumberUtil.add2(10, 20));
        System.out.println(NumberUtil.add2(2.0, 3.0));
    }

}
