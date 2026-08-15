package com.corejava.genericmethod;

/**
 * 在 Java 中，泛型方法（Generic Method）的类型参数声明位置必须放在修饰符之后、返回值类型之前。
 * 例如：public static <T> T identity(T arg) 中，<T> 就在 public static 后面、T（返回值）的前面。
 * 正确格式：修饰符 <泛型参数列表> 返回值类型 方法名(参数列表)
 */
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
