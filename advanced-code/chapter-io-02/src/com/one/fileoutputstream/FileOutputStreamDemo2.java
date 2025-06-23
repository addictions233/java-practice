package com.one.fileoutputstream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author one
 * InputStream和OutputStream是字节流的根抽象类, FileInputStream和FileOutputStream是最重要的继承类
 *  用字节数组读取文件
 */
public class FileOutputStreamDemo2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("D:\\CloudMusic\\平凡之路.mp3");
        FileOutputStream fos = new FileOutputStream("C:\\Users\\one\\Desktop\\平凡之路.mp3");
        byte[] bys = new byte[1024];
        int len;
        // 使用字节输入流一次读取一个字节数组
        while((len = fis.read(bys))!=-1){
            fos.write(bys,0,len);
        }

        fis.close();
        fos.close();
    }
}
