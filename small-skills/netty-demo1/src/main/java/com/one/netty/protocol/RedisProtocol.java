package com.one.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;
import java.util.PrimitiveIterator;

/**
 * @author one
 * @description 使用redis协议与redis服务端进行通信
 * @date 2024-6-12
 */
public class RedisProtocol {

    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        // 换行符对于的Byte数组
        final byte[] LINE = new byte[]{13, 10};
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LoggingHandler());
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                /**
                                 * 会在连接channel建立成功后,触发active事件
                                 */
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf byteBuf = ctx.alloc().buffer();
                                    byteBuf.writeBytes("*3".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    byteBuf.writeBytes("$3".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    byteBuf.writeBytes("set".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    byteBuf.writeBytes("$4".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    byteBuf.writeBytes("name".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    byteBuf.writeBytes("$8".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    byteBuf.writeBytes("zhangsan".getBytes());
                                    byteBuf.writeBytes(LINE);
                                    ctx.writeAndFlush(byteBuf);
                                }
                            });
                        }

                        /**
                         * 服务端响应数据, 会触发read事件
                         */
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf byteBuf = (ByteBuf) msg;
                            // 打印redis服务器端响应的结果数据
                            System.out.println(byteBuf.toString(Charset.defaultCharset()));
                        }

                    });
            // 连接redis服务端
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6379).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            worker.shutdownGracefully();
        }
    }
}
