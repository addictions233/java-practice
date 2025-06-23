package com.one.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.one.nio.util.ByteBufferUtil.debugRead;

/**
 * @ClassName: Server
 * @Description: 阻塞模式: 服务器端一个线程只能处理一个客户端连接
 *               非阻塞模式下: 服务器一个线程客户处理多个客户端连接
 * @Author: one
 * @Date: 2021/10/15
 */
@Slf4j
public class BIOServer {

    public static void main(String[] args) throws IOException {
        // 使用nio来写非阻塞模式,单线程
        // 1,创建服务
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置为非阻塞模式, 默认为阻塞模式
        ssc.configureBlocking(false);

        // 绑定端口
        ssc.bind(new InetSocketAddress(8080));

        // 使用byteBuffer作为数据缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);

        List<SocketChannel> socketChannelList = new ArrayList<>();
        // 虽然非阻塞模式服务端一个线程可以处理多个客户端连接,但是如果没有客户端连接的情况下
        // 非阻塞模式的代码也会不断循环执行,占用资源,没有实际产出
        while (true) {
            // 4. accept建立与客户端连接, SocketChannel用来与客户端通信
            log.info("connecting....");
            // 阻塞模式下accept方法会阻塞
            // 非阻塞模式accept方法会继续往下运行, 但是返回的channel是null
            SocketChannel channel = ssc.accept();
            if (channel != null) {
                // 配置channel为非阻塞模式
                channel.configureBlocking(false);
                socketChannelList.add(channel);
                log.info("connected....");
            }
            for (SocketChannel socketChannel : socketChannelList) {
                log.info("before read....");
                // 接收客户端发送的数据
                // 阻塞模式下read方法会阻塞
                // 非阻塞模式下read方法会继续执行
                socketChannel.read(byteBuffer);
                // byteBuffer从读模式切换为写模式
                byteBuffer.flip();
                debugRead(byteBuffer);
                // 清空,可以重新写入
                byteBuffer.clear();
                log.info("after read.....");
            }
        }
    }
}
