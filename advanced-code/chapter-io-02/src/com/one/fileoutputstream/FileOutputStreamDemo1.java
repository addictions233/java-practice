package com.one.fileoutputstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: FileOutputStreamDemo
 * @Description: 字节输出流  FileOutputStream
 *构造方法 FileOutputStream(String path, boolean append) FileOutputStream(File file, boolean append) 是否追加写
 * void write(int b) 一次写一个字节
 * void write(byte[] b) 一个写一个字节数组
 * void write(byte[] b, int startIndex, int len) 一次写一个字节数组的一部分
 * @Author: one
 * @Date: 2021/07/24
 */
public class FileOutputStreamDemo1 {
    public static void main(String[] args) throws IOException {
        // 创建字节输出流对象, 开启追加写
        FileOutputStream fileOutputStream = new FileOutputStream("My_Day12/abc.txt", true);
        // 使用字节输出流一次写一个字节
        fileOutputStream.write(97);
        fileOutputStream.write(98);
        // 使用字节输出流一次写一个字节数组
        byte[] array = {99, 100, 101};
        fileOutputStream.write(array);

        // 换行
        String changeLine = System.getProperty("line.separator");
        fileOutputStream.write(changeLine.getBytes());
        fileOutputStream.write("我爱你中国".getBytes(StandardCharsets.UTF_8));

        fileOutputStream.close();
    }
}
