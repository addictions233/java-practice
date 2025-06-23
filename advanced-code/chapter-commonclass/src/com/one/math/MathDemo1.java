package com.one.math;

/**
 *  学习数学工具类 Math
 *      如果一个类所有的成员变量和成员方法都用static修饰的,那么该类就是一个工具类,直接用类型.成员名来使用成员
 *      工具类的构造方法会用private修饰,目的是防止在其他类中创建本类对象
 *
 */
public class MathDemo1 {
    public static void main(String[] args) {
        System.out.println(Math.PI);

        /*
            static double pow​(double a, double b)  计算以第一个参数为底数,第二个参数指数的值
         */
        System.out.println(Math.pow(2, 3));  // 输出: 8.0

        System.out.println(Math.random()); // 生成随机数 范围:[0.0 , 1.0)    输出: 0.8748041525214414

    }
}
