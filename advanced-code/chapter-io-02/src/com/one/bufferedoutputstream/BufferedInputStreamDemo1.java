package com.one.bufferedoutputstream;

import java.io.*;

/**
 * @ClassName: BufferedInputStreamDemo1
 * @Description: 高效字节输入输出流
 * @Author: one
 * @Date: 2021/07/26
 */
public class BufferedInputStreamDemo1 {
    public static void main(String[] args) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("My_Day12\\abc.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("My_Day12\\ddd.txt"));

        int byt;
        while((byt = bis.read()) != -1) {
            bos.write(byt);
            // 高效字节流在写出数据时一定要刷新缓存区
            bos.flush();
        }

        //关流
        bis.close();
        bos.close();
    }
}
