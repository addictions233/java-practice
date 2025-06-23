package com.one.nio.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author one
 * @description 测试ByteBuffer的slice方法
 * @date 2025-3-1
 */
@Slf4j
public class SliceTest {

    public static void main(String[] args) {
        byte[] bytes = new byte[]{1,2,3,4,5};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        //使用slice之前，一般先调用position()和limit()方法，
        byteBuffer.position(2);
        // 2 5 5
        log.info("position:{},limit:{},capacity:{}", byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity());
        ByteBuffer byteBuffer1 = byteBuffer.slice();
        // 12345->345
        // 2 5 5
        log.info("position:{},limit:{},capacity:{}", byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity());
        // 0 3 3
        log.info("position:{},limit:{},capacity:{}", byteBuffer1.position(), byteBuffer1.limit(), byteBuffer1.capacity());
        byteBuffer1.put((byte)6);
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
        //使用slice()后，再调用arrayOffset()方法时，会出现返回值非0的情况
        //其是对原缓冲区的偏移
        log.info("offset:{}", byteBuffer1.arrayOffset());
    }
}
