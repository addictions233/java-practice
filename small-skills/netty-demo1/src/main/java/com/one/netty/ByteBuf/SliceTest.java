package com.one.netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.one.netty.ByteBuf.ByteBufTest.log;

/**
 * @author one
 * @description Slice体现了netty对于零拷贝的使用
 * @date 2024-4-8
 */
public class SliceTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log(byteBuf);

        // 在切片的过程中没有发生数据复制
        // slice对原始ByteBuf切片成多个ByteBuf，切片后的ByteBuf并没有发生内存复制，还是使用原始 ByteBuf 的内存，
        // 切片后的 ByteBuf 维护独立的 read，write 指针。
        ByteBuf buf1 = byteBuf.slice(0, 5);
        buf1.retain(); // 引入计数 +1
        ByteBuf buf2 = byteBuf.slice(5, 5);
        log(buf1);
        log(buf2);

        System.out.println("释放原有byteBuf内存");
        byteBuf.release(); // 引用计数 -1
        log(buf1);
    }
}
