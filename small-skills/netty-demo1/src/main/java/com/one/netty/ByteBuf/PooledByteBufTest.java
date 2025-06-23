package com.one.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @author one
 * @description 当ByteBuf使用堆外内存创建时, 创建和销毁时都比较耗时, 所以要考虑池化 POOL
 * @date 2025-1-18
 */
public class PooledByteBufTest {

    public static void main(String[] args) {
        ByteBuf unpoolByteBuf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        System.out.println(unpoolByteBuf.isDirect());
        ByteBuf poolByteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(8, 20);
        System.out.println(poolByteBuf.isDirect());
    }
}
