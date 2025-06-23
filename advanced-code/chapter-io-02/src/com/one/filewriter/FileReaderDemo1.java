package com.one.filewriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: FileReaderDemo1
 * @Description: 如果是输入流,路径结尾对应的文件必须存在, 如果是输出流, 路径对应的文件目录必须存在
 * @Author: one
 * @Date: 2021/07/31
 */
public class FileReaderDemo1 {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("My_Day12\\出师表.txt");
        FileWriter fw = new FileWriter("My_Day12\\出师表复制.txt");

        // 字节流一次读一个字节, 字符流一次读一个字符(两个字节), 都可以被int(四个字节)接收
        // java语言使用的内码编码是utf-16, 即一个字符char占两个字节(包括中文汉字)
        // 调用 str.getBytes()方法是字符串按照外码编码进行转换字节数组, 默认的外码是utf-8
        int ch;
        while ((ch = fr.read()) != -1) {
            fw.write(ch);
            // 高效流和字符流都需要刷新缓冲区
            fw.flush();
        }
        //关流
        fr.close();
        fw.close();
    }
}
