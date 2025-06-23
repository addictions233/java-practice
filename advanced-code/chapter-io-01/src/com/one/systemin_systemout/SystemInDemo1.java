package com.one.systemin_systemout;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * @author one
 * System.in:标准的系统输入流  -> InputStream  普通的字节输入流
 *   	只做键盘录入
 * System.out:标准的系统输出流  -> PrintStream  打印字节输出流
 *   	只做控制台打印
 */
public class SystemInDemo1 {
    public static void main(String[] args) throws IOException {
        /*
            InputStream is = System.in;  System.in返回的是字节输入流的根节点InputStream
                该根节点是一个抽象类,不能直接创建对象
                用System.in返回对象创建一个高效缓冲字节输入流
         */
        BufferedInputStream bis = new BufferedInputStream(System.in);
        /**
         *  java规定: 以回车键表示控制台数据的确认输入,输入Enter键代表着一次键盘录入的结束
         *  当用字节输入流中的read()方法一个一个的字节读取时,由于键盘只能录入各种字符,每个字符都是一个字节
         *  输入Enter键同样也是输入一个字符,Enter键对应的值是10
         */
        int by;
        by = bis.read();   // 一次从控制台键盘录入读取一个字节,每调用一个read()方法就等待这键盘输入一次
        System.out.println("by = " + by);

        int by2;
        by2 = bis.read();
        System.out.println("by2 = " + by2);

        int by3;
        by3 = bis.read();
        System.out.println("by3 = " + by3);
        //关流
        bis.close();
    }

}
