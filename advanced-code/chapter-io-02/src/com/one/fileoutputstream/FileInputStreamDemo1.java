package com.one.fileoutputstream;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @ClassName: FilInputStreamDemo1
 * @Description: 字节输入流 FileOutputStream
 * 构造方法:  FileInputStream(File file) , FileInputStream(String path)
 *   int read() 一次读取一个字节 返回记过是读到的字节
 *   int read(byte[] bys)  一次读取一个字节数组, 返回的结果是读到的字节数组的长度
 *   int read(byte[] bys, int startIndex, int length): 一次读取一个字节数组的一部分
 * @Author: one
 * @Date: 2021/07/24
 */
public class FileInputStreamDemo1 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("My_Day12\\abc.txt");
        byte[] bys = new byte[1024];
        // len表示读到的字节个数
        int len;
        while((len = fis.read(bys)) != -1) {
            // 读到了len个子节,就转换len个子节
            System.out.println(new String(bys,0,len));
        }

        //关流
        fis.close();
    }
}
