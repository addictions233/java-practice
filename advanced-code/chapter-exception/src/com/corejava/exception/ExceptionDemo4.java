package com.corejava.exception;

/**
 *  自定义异常
 */
public class ExceptionDemo4 {
    public static void main(String[] args) throws MyCompileException {
//        throwMyException1(-1);
//        throwMyException2(-1);

    }

    public static void throwMyException1(int num){
        if(num == -1){
            throw new MyRuntimeException("这是我自己定义的运行时异常");   // 运行时异常
        } else{
            System.out.println(num);
        }
    }
    public static void throwMyException2(int num) throws MyCompileException {
        if(num == -1){
            throw new MyCompileException("这是我自己定义的编译时异常");
        } else{
            System.out.println(num);
        }
    }
}

/**
 *  自己定义一个编译时异常类 MyCompileException
 */
class MyCompileException extends Exception{
    //自动生成无参构造
    public MyCompileException() {
    }
    //自动生成有参构造
    public MyCompileException(String message) {
        super(message);
    }
}

/**
 *  自己定义一个运行时异常类 MyRuntimeException
 */
class MyRuntimeException extends RuntimeException{
    //自动生成空参构造
    public MyRuntimeException() {
    }
    //自动生成有参构造
    public MyRuntimeException(String message) {
        super(message);
    }
}
