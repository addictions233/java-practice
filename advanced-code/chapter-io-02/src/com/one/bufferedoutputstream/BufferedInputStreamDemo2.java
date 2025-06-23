package com.one.bufferedoutputstream;

import java.io.*;

/**
 * @ClassName: BufferedInputStreamDemo2
 * @Description: 标准的高效字节流输入代码
 * @Author: one
 * @Date: 2021/07/27
 */
public class BufferedInputStreamDemo2 {
    public static void main(String[] args) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("My_Day12\\abc.txt"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("My_Day12\\ddd.txt"));

        // 一次读取一个字节数组
        byte[] bys = new byte[1024];
        // 每次读取到的字节数组的长度
        int length;

        while((length = bufferedInputStream.read(bys)) != -1) {
            // 写出数据
            bufferedOutputStream.write(bys,0,length);
            // 高效流在写出数据时一定要刷新缓冲区
            bufferedOutputStream.flush();
        }
        // 关流
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }
}
