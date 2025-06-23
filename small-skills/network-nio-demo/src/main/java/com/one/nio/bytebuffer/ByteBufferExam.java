package com.one.nio.bytebuffer;

import com.one.nio.util.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @ClassName: ByteBufferExam
 * @Description: 使用ByteBuffer处理黏包和半包问题
 * @Author: one
 * @Date: 2021/09/23
 */
public class ByteBufferExam {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        // 模拟byteBuffer接收到的第一条消息
        byteBuffer.put("Hello,world\nI'm Zhangsan\nHo".getBytes());
        // 拆分消息
        split(byteBuffer);
        // 模拟byteBuffer接收到的第二条消息
        byteBuffer.put("w are you?\n".getBytes());
        // 拆分消息
        split(byteBuffer);
    }

    /**
     * 将byteBuffer中的消息内容按照\n分隔符进行拆分
     * @param byteBuffer byteBuffer
     */
    public static void split(ByteBuffer byteBuffer) {
        // 切换到读模式
        byteBuffer.flip();
//        ByteBufferUtil.debugAll(byteBuffer);
        // byteBuffer.limit()方法获取的是byteBuffer中限制位置
        for (int i = 0; i < byteBuffer.limit(); i++) {
            // byteBuffer.get(index)方法的position读指针不移动
            if(byteBuffer.get(i) == '\n') {
                // 由于byteBuffer.get(index)方法position读指针不移动,所以position还是0
                System.out.println("position:" + byteBuffer.position());
                System.out.println("i:" + i);
                int length = i + 1 - byteBuffer.position();
                ByteBuffer buffer = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    // byteBuffer.get()方法position读指针移动
                    buffer.put(byteBuffer.get());
                }
                // 打印读取到的byteBuffer
                ByteBufferUtil.debugAll(buffer);
            }


        }
        // 切换到写模式,要保留未读取到的数据,所以用compact()方法
        byteBuffer.compact();
    }
}
