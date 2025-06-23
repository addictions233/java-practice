package com.one.bufferedoutputstream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author one
 *  带缓冲区的高效字节输入输出流: BufferedInputStream, BufferedOutputStream
 *  注意点: 1,由于携带缓存区所以一次性可以读取更多的数据,故名高效字节流
 *          2, 每次写出数据时,都需要调用flush()方法将数据从缓冲区中刷出来
 *          3, 高效字节流并没有创建一个新的流对象,只是对字节输入输出流的一种包装,高效流读和写的方法和字节流的方法一模一样
 */
public class BufferedOutputStream1 {
    public static void main(String[] args) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("My_Day12\\abc.txt"));
        bos.write("劝君更尽一杯酒".getBytes());
        //刷新
        bos.flush();
        bos.write("\r\n".getBytes());
        bos.flush();
        bos.write("西出关外无故人".getBytes());
        bos.flush();
        bos.close();
    }
}
