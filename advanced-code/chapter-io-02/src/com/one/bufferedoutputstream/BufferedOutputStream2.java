package com.one.bufferedoutputstream;

import java.io.*;

/**
 * 带缓冲区的字节数组输入输出流
 */
public class BufferedOutputStream2 {
    public static void main(String[] args) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\one\\Desktop\\平凡之路1.mp3"));
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\CloudMusic\\平凡之路.mp3"));
        byte[] bys = new byte[1024];
        int len;
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
            bos.flush(); // 注意刷新,将缓冲区的字节数组刷进输出文件中
        }
        bis.close();
        bos.close();
    }
}
