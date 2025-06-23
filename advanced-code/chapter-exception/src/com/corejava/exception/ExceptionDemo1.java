package com.corejava.exception;

/**
 *  Exception的子类异常分类: (区别于 Error异常)
 *      运行时异常:就算你代码写错了,在编译阶段都不会报错,只会等到运行时期出现了错误才会报错
 *          注意: 1, 运行时异常可以在编写代码阶段不做处理,因为编译不会给你报错
 *                         IDEA是自动对编辑的源码进行编译的,而代码的运行则需要手动启动
 *                2, 运行时异常也可以用 try...catch....语句进行捕获处理
 *      编译时异常:就算你写对代码在编译阶段都会报错,必须让你手动的去处理编译时期异常
 *          注意: 编译时异常必须在编写代码阶段就进行处理,处理编译时异常的两种方法:
 *                   1,抛出去 -> 谁调用此方法谁处理
 *                          必须在方法声明上抛出该异常(用 throws关键字),谁调用该方法谁就必须抛出该异常
 *                   2,包起来 -> 程序员自己处理
 *  处理异常的两种方式:
 *      1,在方法的声明上用throws关键字抛出异常,交给jvm处理
 *          jvm又是怎么处理代码编写人员抛给自己的异常呢?
 *              运行到有异常的语句时,程序运行终止,后面代码不再执行,jvm在控制台打印输出异常对象,并给出造成异常的代码行
 *      2,自己用try...catch..语句对异常进行处理
 */
public class ExceptionDemo1 {
    public static void main(String[] args) throws ArithmeticException {
        System.out.println("start");
        // 运行时异常
        System.out.println(7 / 0);
        System.out.println("end");
    }
}
