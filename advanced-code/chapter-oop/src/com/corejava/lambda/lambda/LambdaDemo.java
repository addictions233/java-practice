package com.corejava.lambda.lambda;

/**
 * @author one
 */
public class LambdaDemo {
    private final static String CONSTANT = "hello";

    public static void main(String[] args) {
        UserHandler userHandler = new UserHandlerImpl();
        String message = "abc";
        userHandler.handleParam(message, (targetStr) -> {
            // 进行字符串翻转
            return new StringBuilder(targetStr).reverse().toString();
        });

        /**
         * lambda表达式本质上它所在类的一个静态方法, 它的参数有两种来源:
         *  1, 一种是它所处的方法的上下文, 包括lambda表达式所在的方法中局部变量, 已经它所处的对象中的成员变量 local variable can not be
         *  2, 一种lambda表达式作为方法本身可以传入的形参, 如果lambda表达式向获取非它所处的方法中的局部变量
         *  必须通过形参进行传入
         */
        String message2 = "ABC";
        userHandler.handleParam2(message2, () -> {
            // 进行字符串翻转
            System.out.println(CONSTANT);
            return new StringBuilder(message2).reverse().toString();
        });
    }
}
