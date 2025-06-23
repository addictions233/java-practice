package com.one.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @ClassName: Client
 * @Description: 使用nio来编写阻塞模式
 * @Author: one
 * @Date: 2021/10/15
 */
public class Client {

    public static void main(String[] args) throws IOException {
        // SocketChannel用于客户端, 客户端并不需要建立多个连接, 用阻塞和非阻塞模式都可以
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        System.out.println("waiting....");
        socketChannel.write(Charset.defaultCharset().encode("hello"));
    }
}
