package com.one.nio.fileChannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @ClassName: TransferToTest
 * @Description: transferTo()方法,进行零拷贝复制, 但是一次最多复制2G大小的数据, 将数据从一个fi
 * @Author: one
 * @Date: 2021/10/07
 */
public class TransferToTest {
    public static void main(String[] args) {
        try {
            // 创建一个读模式的FileChannel对象
            FileChannel from = new FileInputStream("netty-demo1\\data.txt").getChannel();
            // 创建一个写模式的FileChannel对象
            FileChannel to = new FileOutputStream("netty-demo1\\to.txt").getChannel();
            // 将读模式的FileChannel中的数据拷贝到写模式的FileChannel中
            // transferTo()方法的一次拷贝上限是2G,如果大于2G的文件,需要分批多次拷贝
            // 由于零拷贝在用户空间只保留了地址值, 不保留文件实际内容, 所以操作的文件大小不能太大, 不能超过2G
            long size = from.size();
            for(long left = size; left > 0;) {
                // transferTo()方法会返回读取的字节个数
                left -= from.transferTo(size - left, size, to);
                System.out.println("position:" + (size -left) + " left:" + left);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
