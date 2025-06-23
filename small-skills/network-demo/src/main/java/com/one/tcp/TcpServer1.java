package com.one.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author one
 */
public class TcpServer1 {
    public static void main(String[] args) throws IOException {
        //创建服务端的ServerSocket对象,指定 端口号
        ServerSocket serverSocket = new ServerSocket(10086);
//        serverSocket.bind(new InetSocketAddress(10086));
        System.out.println("start server...");
        // 启动服务器,与客户端建立连接
        while(true){
            // BIO模型: 阻塞IO, accept方法等待客户端建立连接是阻塞的
            Socket socket = serverSocket.accept();
            //创建高效字符输入流对象
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 创建数据输出流对象
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // 从客户端获取字符串
            String readLine = inFromClient.readLine();
            // 将字符串转换为大写
            String newLine = readLine.toUpperCase()+"\n";
            //输出到客户端
            dataOutputStream.writeBytes(newLine);

        }
    }
}
