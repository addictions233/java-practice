package com.one.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static com.one.netty.ByteBuf.ByteBufTest.log;

/**
 * @author one
 * @description CompositeByteBuf: 合并多个ByteBuffer
 * @date 2024-4-8
 */
public class CompositeByteBufTest {

    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{'a','b','c','d','e'});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{'f','g','h','i','j'});

        // CompositeByteBuf 是一个组合的 ByteBuf，它内部维护了一个 Component 数组，每个 Component 管理一个 ByteBuf，
        // 记录了这个 ByteBuf 相对于整体偏移量等信息，代表着整体中某一段的数据。对外是一个虚拟视图，组合这些 ByteBuf 不会产生内存复制。
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeByteBuf.addComponents(true, buf1, buf2);
        log(compositeByteBuf);
    }
}
