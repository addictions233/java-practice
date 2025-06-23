package com.one.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;


/**
 * @author one
 * @description 创建http协议的服务器端
 * @date 2024-6-12
 */
@Slf4j
public class HttpProtocol {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LoggingHandler());
                            // 添加http协议的编/解码器
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                            // 这里的泛型表示只关注请求头和请求行数据, 不关注请求体, 处理Get请求不用关注请求体
                            socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) throws Exception {
                                    log.info("请求uri" + httpRequest.uri());
                                    // 构建响应对象
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                    // 构建响应内容
                                    byte[] bytes = "<h1>hello world!<h1>".getBytes();
                                    // 响应头中设置响应体的长度
                                    response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
                                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
                                    response.content().writeBytes(bytes);
                                    channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) {
                                    ctx.flush();
                                }
                            });

                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8090).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
