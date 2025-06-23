package com.one.tcp.demo2;

import com.sun.media.sound.SoftTuning;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * @ClassName: SocketClient
 * @Description: Socket网络编程中的客户端
 * @Author: one
 * @Date: 2021/08/15
 */
public class SocketClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 构造方法: ip地址和端口号
        Socket socket = new Socket("127.0.0.1",10087);
        // 利用socket对象创建输出流,向服务端发送消息数据
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        System.out.println("客户端发送了消息...");
        dataOutputStream.writeBytes("hello, this is message coming from client");
        // 网络IO和磁盘IO不同,如果网路IO客户端不关闭输出流, 服务端是无法判断客户端输入的结束标志是什么
        // 这样就会造成服务端的读操作一直阻塞,在等待客户端发送写操作结束的标志
        socket.shutdownOutput();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        //readLine是一个阻塞的方法，只要没有断开连接，就会一直等待，直到有东西返回，那么什么时候返回空呢，只有读到数据流最末尾，才返回null
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        // 关流
        socket.close();
    }
}
