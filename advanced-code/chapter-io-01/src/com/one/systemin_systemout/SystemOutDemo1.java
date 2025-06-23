package com.one.systemin_systemout;

import java.io.PrintStream;

/**
 * System.out:标准的系统输出流  -> PrintStream  打印字节输出流
 *   	只做打印控制台
 * System类中:
 *      static PrintStream  out “标准”输出流。
 */
public class SystemOutDemo1 {
    public static void main(String[] args) {
        PrintStream ps = System.out;
        ps.println("hello"); // 控制台输出: hello , 换行
        ps.print("java");  // 控制台输出: java , 不换行
    }
}
