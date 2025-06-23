package com.corejava.exception;

/**
 *  当我们把异常抛给jvm,jvm又是怎么处理异常的呢?
 *      运行时异常:就算你在编写代码时期写错了也不会报错,只有等到运行时期才会报错
 *          例如: System.out.println(7/0);
 *          1,在代码报错行立刻终止程序,代码报错行前面的代码都会执行
 *          2,打印异常信息在控制台
 *              -> 以红色字体打印
 *              -> 告知异常名称,全类名
 *              -> 打印异常可能产生的原因
 *              -> 异常可能出现的代码行号
 *          3,打印异常信息会开辟新的线程,并告知异常出现在哪个线程当中
 */
public class ExceptionDemo2 {
    public static void main(String[] args)  {
        System.out.println("start");
        System.out.println("start");
        System.out.println("start");
        System.out.println("start");
        System.out.println("start");
        System.out.println("start");

        //只有运行到该行代码时jvm才会抛出异常
        try {
            System.out.println(7/0);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        dateFormat.parse("1990年08月08日 18:08");
        System.out.println("end");


    }
}
