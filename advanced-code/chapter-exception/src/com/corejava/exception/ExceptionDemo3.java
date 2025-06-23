package com.corejava.exception;

import java.text.ParseException;

/**
 *  方法中是如何抛出异常的?
 *      Throwable接口中的printStackTrace()方法
 *  throws关键字:  在方法声明上使用,抛出一个异常名称,给jvm处理
 *  throw关键字: 在方法体中使用,抛出一个新创建的异常对象,暴露出去
 */
public class ExceptionDemo3 {
    public static void main(String[] args) throws ParseException {
//        throwException(-1);  // 可以在编写代码时不做处理,运行时才会抛出 NullPointerException
        throwException2(-1);  // 编译时异常 在代码编写阶段必须在调用该异常的方法签名上抛出该异常

    }

    public static void throwException(int num){
        if(num == -1){
            throw new NullPointerException("这是我想要抛出的运行时异常"); //运行时异常
        } else{
            System.out.println("num="+num);
        }
    }

    public static void throwException2(int num) throws ParseException {
        if(num == -1){
            throw new ParseException("这是我想要抛出的编译时异常",1); // 编译时异常
        } else{
            System.out.println("num=" + num);
        }
    }
}
