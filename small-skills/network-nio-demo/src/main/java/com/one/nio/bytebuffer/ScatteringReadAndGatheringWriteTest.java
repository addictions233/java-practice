package com.one.nio.bytebuffer;

import com.one.nio.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: ByteBufferScatteringReadAndGatheringWrite
 * @Description: ByteBuffer的分散读和集中写
 * @Author: one
 * @Date: 2021/09/22
 */
@Slf4j
public class ScatteringReadAndGatheringWriteTest {
    public static void main(String[] args) {
        // 演示: 分散读
        // 参数"r"表示读模式, "rw"表示读写模式
        try (FileChannel channel = new RandomAccessFile("netty-demo1\\word.txt","r").getChannel()) {
            // 创建三个byteBuffer对象
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(3);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(3);
            ByteBuffer byteBuffer3 = ByteBuffer.allocate(5);
            // 使用channel对象将文本中的内容分散读取到三个byteBuffer对象中
            channel.read(new ByteBuffer[] {byteBuffer1,byteBuffer2,byteBuffer3});
            // 打印结果
            ByteBufferUtil.debugAll(byteBuffer1);
            ByteBufferUtil.debugAll(byteBuffer2);
            ByteBufferUtil.debugAll(byteBuffer3);
        } catch (IOException e) {
            log.error("throw exception:", e);
        }

        //演示: 集中写
        try (FileChannel fileChannel = new RandomAccessFile("netty-demo1\\word2.txt", "rw").getChannel()) {
            ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
            ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("world");
            ByteBuffer buffer3 = StandardCharsets.UTF_8.encode("你好");
            fileChannel.write(new ByteBuffer[] {buffer1,buffer2,buffer3});
        } catch (IOException e) {
            log.error("throw Exception:", e);
        }
    }
}
