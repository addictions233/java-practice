package com.one.nio.bytebuffer;

import com.one.nio.util.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName: ByteBufferStringTest
 * @Description: ByteBuffer和String字符串字节的互相转换
 * @Author: one
 * @Date: 2021/09/20
 */
public class ByteBufferStringTest {
    public static void main(String[] args) {
        /*
         * 将字符串转换为ByteBuffer的三种方式:
         *  1, String.getBytes()  2, StandardCharset.encode()  3, ByteBuffer.wrap()静态方法
         */
        // 1,调用String.getBytes()将字符串转换为字节数组,然后调用put()方法将字节数组保存到byteBuffer对象中
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
        byteBuffer1.put("hello".getBytes(StandardCharsets.UTF_8));
        ByteBufferUtil.debugAll(byteBuffer1);

        // 2,调用StandardCharset.encode()方法直接获取ByteBuffer对象
        ByteBuffer byteBuffer2 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(byteBuffer2);

        // 3,使用ByteBuffer.wrap()静态方法
        ByteBuffer byteBuffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
        ByteBufferUtil.debugAll(byteBuffer3);

        /**
         * 将ByteBuffer对象转换为String字符串: StandardCharset.decode()方法
         */
        String str = StandardCharsets.UTF_8.decode(byteBuffer3).toString();
        System.out.println(str);

        // byteBuffer1对象是调用put()方法,要先切换到写模式,才能转换为字符串
        byteBuffer1.flip();
        String str2 = StandardCharsets.UTF_8.decode(byteBuffer1).toString();
        System.out.println(str2);

    }
}
