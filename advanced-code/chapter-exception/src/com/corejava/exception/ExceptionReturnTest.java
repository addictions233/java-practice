package com.corejava.exception;

/**
 * @ClassName: ExceptionReturnTest
 * @Description: 测试在Catch语句之后的return语句是否会执行
 * @Author: one
 * @Date: 2021/10/22
 */
public class ExceptionReturnTest {
    public static void main(String[] args) {
        System.out.println(exceptionReturn());
    }

    public static String exceptionReturn() {
        try {
            int i = 1 / 0;
            return "nomarlly execute";
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将异常进行捕获之后, catch块之后的代码正常执行
        return "throw a exception";
    }
}
