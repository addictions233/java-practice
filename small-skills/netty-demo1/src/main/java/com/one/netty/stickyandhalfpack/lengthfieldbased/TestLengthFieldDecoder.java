package com.one.netty.stickyandhalfpack.lengthfieldbased;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;



/**
 * @author one
 * @description EmbeddedChannel模拟出站和入站的操作, 底层不进行实际的传输, 不需要启动Netty服务器和客户端 用于ChannelHandler业务处理器的单元测算
 * @date 2024-6-11
 */
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        // maxFrameLength: 最大长度
        // lengthFieldOffset: 长度字段的偏移量
        // lengthFieldLength: 长度字段的长度
        // lengthAdjustment: 长度字节和内容字节中间的补偿长度
        // initialBytesToStrip: 剥离字节数, 从头开始剥离几个字节, 可能是标识文件类型什么的
        EmbeddedChannel channel = new EmbeddedChannel(new LengthFieldBasedFrameDecoder(1024,0,4,1,0),
                new LoggingHandler(LogLevel.DEBUG));
        // 4个字节的内容长度, 实际内容
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        send(byteBuf, "hello world");
        send(byteBuf, "hi");
        channel.writeInbound(byteBuf);
    }

    private static void send(ByteBuf byteBuf, String content) {
        // 需要发送的内容的字节数组
        byte[] bytes = content.getBytes();
        // 字节数组的长度
        int length = bytes.length;
        // 一个int占4个字节, 长度字段4个字节, 长度字段的偏移量为0
        byteBuf.writeInt(length);
        byteBuf.writeByte(1);
        byteBuf.writeBytes(bytes);
    }
}
