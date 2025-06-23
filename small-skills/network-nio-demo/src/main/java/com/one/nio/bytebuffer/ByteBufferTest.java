package com.one.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName: ByteBufferTest
 * @Description: netty三大组件: channel, buffer, selector   ByteBuffer的使用入门案例
 * @Author: one
 * @Date: 2021/09/14
 */
@Slf4j
public class ByteBufferTest {
    public static void main(String[] args) {
        // 获取 FileChannel对象:  Channel分类: FileChannel, SocketChannel, ...
        // 1, 通过输入输出流获取FileChannel对象  2, 通过RandomAccessFile获取Channel对象
        try (FileChannel fileChannel = new FileInputStream("nio-demo\\data.txt").getChannel()) {
            // 创建 ByteBuffer 字节缓冲区对象, 通过静态工厂方法
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            // 定义变量保存每次读取到的字节个数
            int length;
            // 将文件channel中读取到的数据写入到byteBuffer缓冲区中
            while((length = fileChannel.read(byteBuffer)) != -1) {
                log.info("读取到的字节个数:{}", length);
                // 打印byteBuffer中读到的内容
                // 先将缓冲区切换到读模式
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
                    // 将读取到的字节强转为字符
                    System.out.println((char) b);
                }
                // 清空缓冲区
                byteBuffer.clear();
            }
        } catch (IOException e) {
            log.error("throw Exception:",e);
        }
    }
}
