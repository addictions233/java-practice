package com.one.nio.bytebuffer;

import com.one.nio.util.ByteBufferUtil;

import java.nio.ByteBuffer;


/**
 * @ClassName: ByteBufferReadWriteTest
 * @Description: ByteBuffer的读写案例
 * @Author: one
 * @Date: 2021/09/17
 */
public class ByteBufferReadWriteTest {
    public static void main(String[] args) {
        // 创建一个 capacity为10的字节缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        // 向字节缓冲区中添加一个数字 0x61表示十六进制的97, 字符'a'
        byteBuffer.put((byte) 0x61);
        // 打印字节缓冲区
        ByteBufferUtil.debugAll(byteBuffer);
        // 向字节缓冲区中添加一个字节数组
        byteBuffer.put(new byte[] {0x62,0x63,0x64});
        ByteBufferUtil.debugAll(byteBuffer);
        // 将byteBuffer切换到读模式
        byteBuffer.flip();
        // byteBuffer的读模式类似于集合遍历中的迭代器,每读取一个元素,读指针向前移动一步
        byte byt1 = byteBuffer.get();
        // 输出 97
        System.out.println(byt1);
        byte byt2 = byteBuffer.get();
        // 输出 98
        System.out.println(byt2);
        // 将byteBuffer切换到写模式
        // compact()方法是保留读指针position后面还没有读取的内容
//        byteBuffer.compact();
        // clear()方法是清空byteBuffer中所有的内容
        byteBuffer.clear();
        // int类型强转为byte类型
        byteBuffer.put((byte)0x65);
        byteBuffer.put((byte)0x66);
        // 遍历byteBuffer,打印所有的内容
        //切换到读模式
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }

    }
}
