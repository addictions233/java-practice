package com.one.nio.fileChannel;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName: FileChannelMethodTest
 * @Description: 文件IO: FileChannel只能工作在阻塞模式下,不能配合Selector工作在非阻塞模式下
 *      获取FileChannel对象的三种方式:
 *              1,通过 FileInputStream获取读模式的FileChannel对象
 *              2,通过 FileOutputStream获取写模式的FileChannel对象
 *              3, 通过RandomAccessFile获取"rw"可读可写模式的FileChannel对象
 * @Author: one
 * @Date: 2021/09/26
 */
public class FileChannelMethodTest {
    public static void main(String[] args) {
        //fileChannel.transferTo()方法,零拷贝,效率高
        // 通过 fileInputStream对象创建读模式的fileChannel对象
        // 通过 fileOutputStream对象创建写模式的fileChannel对象
        try (FileChannel from = new FileInputStream("netty-demo1\\data.txt").getChannel()) {
            try (FileChannel to = new FileOutputStream("netty-demo1/copy.txt").getChannel()) {
                // fileChannel.transferTo()方法进行零拷贝,这种文件负责的效率较高, 比用输入输出流进行复制的效率要高
                // 减少了内核空间拷贝到用户空间的拷贝, 用户空间只保存地址,不保存文件内容, 所以减少了拷贝次数
                // NIO中的transfer方式的零拷贝, 这种拷贝方式不光拷贝硬盘文件, 还可以作为底层硬件之间的拷贝实现
                // 例如kafka使用这种方式将消息从硬盘拷贝到网卡
                // fileChannel.size()获取文件的大小
                from.transferTo(0, from.size(), to);
                // 操作系统处于性能的考虑,会将数据缓存,不是立刻写入磁盘,
                // 可以调用force(true)方法将文件内容和元数据(文件的权限等信息)立刻写入磁盘
                to.force(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
