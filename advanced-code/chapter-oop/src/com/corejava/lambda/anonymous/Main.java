package com.corejava.lambda.anonymous;

/**
 * @ClassName: Main
 * @Description: 在匿名内部类中使用局部变量:
 *              在JDK8之前，如果我们在匿名内部类中需要访问局部变量，那么这个局部变量必须用final修饰符修饰
 * @Author: one
 * @Date: 2021/06/19
 */
public class Main {
    public static void main(String[] args) {
        String str = "aaa";
        /**
         * 在方法中使用匿名内部类,必然有可能用到该方法中的局部变量
         */
        new Thread() {
            @Override
            public void run() {
                System.out.println(str + "bbbb");
            }
        }.start();
        // 匿名内部中使用的局部变量必须是用final修饰的常量或者根据上下文判断值不会改变的局部变量
//        str  ="ccccc";  // 这样写编译会报错, str在匿名内部类中使用了,所用地址值不能发生变化
    }
}
