package com.one.changestream;

import java.io.*;

/**
 * @ClassName: ChangeStreamDemo2
 * @Description:
 *  转换流:  InputstreamReader 和 OutputStreamWriter
 *  FileReader继承自InputStreamReader,FileWriter继承自OutputStream,所以说字符流本身就是通过转换流对字节流的封装,
 *  封装流需要刷新缓冲区,转换流可以将字节流对象转换为字符流对象,建立起字节流和字符流对象的桥梁
 *
 *  字符输出流: 一次写一个字符
 *              一次写一个字符数组
 *              一次写一个字符数组的一部分
 *              一次写一个字符串
 *              一次写一个字符串的一部分0-
 * @Author: one
 * @Date: 2021/08/01
 */
public class ChangeStreamDemo2 {
    public static void main(String[] args) throws IOException {
       // 创建转换流对象
        InputStreamReader isr  = new InputStreamReader(new FileInputStream("My_Day13\\转换流.txt"));
        OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream("My_Day13\\转换流2.txt"));

        int ch;
        while((ch = isr.read()) != -1) {
            osr.write(ch);
            // 字符流都算封装流,都需要刷新缓冲区
            osr.flush();
        }

        // 关流
        isr.close();
        osr.close();
    }
}
