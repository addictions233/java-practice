package com.one.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author one
 */
public class TcpClient1 {
    public static void main(String[] args) throws IOException {
        //1,创建高效字符流从控制台读取一段字符串
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        // 2,创建 流式套接字对象（SOCK_STREAM),指定服务器端IP地址和端口号
        // 指定连接服务端的Ip和端口
        Socket socket = new Socket("127.0.0.1", 10086);
//        socket.connect(new InetSocketAddress("127.0.0.1", 9876));
        // 3,创建客服端数据流对象
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        // 4,创建客服端高效字符输入流对象从服务器端接收字符串
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 5,从用户读取字符串
        String line = inFromUser.readLine();
        // 6,将字符串传递给服务器端
        dataOutputStream.writeBytes(line+"\n");
        // 7,接收服务器端返回数据
        String result = inFromServer.readLine();
        System.out.println(result);
        inFromServer.close();
        socket.close();
        dataOutputStream.close();
    }
}
