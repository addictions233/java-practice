package com.one.nio.selector;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

import static com.one.nio.util.ByteBufferUtil.debugRead;

/**
 * @author one
 * @description 使用selector编写非阻塞IO
 * @date 2024-3-30
 */
@Slf4j
public class SelectorServer {

    public static void main(String[] args) throws IOException {
        // 创建selector,用来把事件分发给感兴趣的channel
        Selector selector = Selector.open();
        // 创建channel, ServerSocketChannel用于服务器端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 将channel设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));

        // 2.建立selector和channel的联系
        // 将channel注册到selector中会生成对应的selectionKey
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0);
        // 这是设置key只关注accept事件
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}", selectionKey);

        while (selector.select() > 0) {
            // 4. 处理事件,selectedKeys中包含了所有的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 处理key时,要从selectedKeys集合中删除,否则下次处理就会有问题
                iterator.remove();
                log.debug("key:{}", key);

                if (key.isAcceptable()) {
                    // 获取连接:accept事件
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel channel1 = channel.accept();
                    channel1.configureBlocking(false);
                    // 将channel注册到selector中
                    SelectionKey key1 = channel1.register(selector, 0);
                    key1.interestOps(SelectionKey.OP_READ);
                    log.debug("{}", channel);
                    log.debug("{}",key1);

                } else if (key.isReadable()) {
                    // 读取数据: readable事件
                    try {
                        SocketChannel channel2 = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
                        channel2.read(byteBuffer);
                        byteBuffer.flip();
                        debugRead(byteBuffer);
                        byteBuffer.clear();
                        log.debug("{}", channel2);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 客户端端断开连接要取消key
                        key.cancel();
                    }
                }
            }
        }


    }
}
