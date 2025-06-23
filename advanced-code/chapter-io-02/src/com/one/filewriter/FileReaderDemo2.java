package com.one.filewriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: FileReaderDemo2
 * @Description: 字符流一次读取一个字符数组: 高效流和字符流都是对字节流的封装,所以都需要刷新缓冲区
 * @Author: one
 * @Date: 2021/07/31
 */
public class FileReaderDemo2 {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("My_Day12/出师表.txt");
        FileWriter fw = new FileWriter("My_Day12/出师表2.txt");
        // 一个读取一个字符数组
        int length;
        char[] charArray = new char[1024];
        while((length = fr.read(charArray)) != -1) {
            fw.write(charArray,0,length);
            // 高效流和字符流都要刷新缓冲区
            fw.flush();
        }
        //关流
        fr.close();
        fw.close();
    }
}
