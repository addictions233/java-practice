package com.one.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @author one
 * @description ByteBuf的自动扩容特性
 * @date 2024-4-8
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        // PooledUnsafeDirectByteBuf 默认采用的是池化的,直接内存的实现
        // 直接内存读写效率较高, 但是创建和回收比较耗时
        // 直接内存创建和销毁的代价昂贵，但读写性能高（少一次内存复制），适合配合池化功能一起用
        System.out.println(byteBuf);
//        ByteBuf heapBuffer = ByteBufAllocator.DEFAULT.heapBuffer(); // 使用堆内存
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");
        }
        byteBuf.writeBytes(sb.toString().getBytes());
        // byteBuf自动扩容从256到512
        System.out.println(byteBuf);
    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
