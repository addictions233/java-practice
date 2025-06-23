package com.one.changestream;

import java.io.*;

/**
 * @ClassName: ChangeStreamDemo3
 * @Description: 转换流可以按照字符的字符编码格式来进行读取数据和写出数据
 *   转换流是唯一一个带有设置读写的字符编码格式的流对象
 *   InputStream(InputStream in, String charsetName): 按照指定的编码格式读取文件
 *   OutputStream(OutputStream out, String charsetName): 按照指定的编码格式写出数据
 * @Author: one
 * @Date: 2021/08/01
 */
public class ChangeStreamDemo3 {
    public static void main(String[] args) throws IOException {
//        GBKWrite();
        //创建按照GBK的字符编码格式读取文件的字符
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("My_Day13/GBK.txt"),"GBK"));
        String line;
        while((line = br.readLine()) != null) {
            System.out.println(line);
        }
        br.close();
    }

    private static void GBKWrite() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("My_Day13/转换流.txt")));
        //通过转换流创建高效字符输出流时指定输出时的字符编码格式,这里输出流指定字符编码格式为 GBK
        // 将 utf-8编码格式的字符文件读取出来并按照 GBK的编码格式写入到文件中
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("My_Day13/GBK.txt"),"GBK"));

        String line;
        while((line = br.readLine()) != null) {
            // 一次写出一行字符串
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        //关流
        bw.close();
        br.close();
    }
}
