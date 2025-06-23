package com.one.math;
//加入这条语句
import static java.lang.Math.*;
/*
    添加这条语句就不用在数学方法和数学常量前写"Math."
 */

/**
 * @PackageName: com.corejava.math
 * @ClassName: Mathlearning
 * @Description: 学习基本类 Math
 * @Author: one
 * @Date: 2020-9-5 8:57
 */
public class Mathlearning {
    public static void main(String[] args) {
        //幂运算 Math.pow(x,a) 计算 x 的 a 次幂
        System.out.println(Math.pow(2,3));

        // 计算一个数值的平方根 . Math.sqrt(x) 将 x 开根号
        System.out.println("The square root of \u03C0 is "+ sqrt(PI));

        System.out.println(PI);   // Math.PI  π
        System.out.println(E);    // Math.E 底数
    }
}
