package com.one.netty.ByteBuf;

import com.one.nettybasic.serializable.protobuf.PersonProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @description: TODO
 * @author: wanjunjie
 * @date: 2024/07/01
 */
public class ByteBufTest2 {

    public static void main(String[] args) {
        // 测试ByteBuf中的读写指针
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeInt(8);
        byteBuf.writeInt(4);
        ByteBufTest.log(byteBuf);
        int length = byteBuf.readableBytes();
        int i = byteBuf.readInt();
        int length2 = byteBuf.readableBytes();
        System.out.println("length:" + length);
        System.out.println("i:" + i);
        System.out.println("length2:" + length2);
        ByteBufTest.log(byteBuf);
    }
}
