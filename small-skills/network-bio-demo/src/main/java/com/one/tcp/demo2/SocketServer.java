package com.one.tcp.demo2;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName: SocketServer
 * @Description: Socket网络编程中的服务端
 * @Author: one
 * @Date: 2021/08/15
 */
public class SocketServer {
    public static void main(String[] args) throws IOException {
        // 创建服务端ServerSocket对象
        ServerSocket serverSocket = new ServerSocket(10087);
        while (true) {
            // 通过ServerSocket对象获取socket对象, 调用accept()方法默认是阻塞的
            Socket socket = serverSocket.accept();
            System.out.println("服务器端建立了连接");
            InputStream inputStream = socket.getInputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            byte[] bys = new byte[1024];
            int length;
            StringBuilder sb = new StringBuilder();
            // socket如果不关闭的话，read之类的阻塞函数会一直等待它发送数据，就是所谓的阻塞
            while((length = inputStream.read(bys)) != -1) {
                sb.append(new String(bys,0,length));
            }
            System.out.println(sb);
            // 向客户端写数据
            dataOutputStream.writeBytes("this is message coming from server");
            // 在写方调用socket的shutdownOutput方法关闭输出流，该方法的文档说明为，将此套接字的输出流置于“流的末尾”，
            // 这样另一端的输入流上的read操作就会返回-1。但是这样不能实现多次交互。
            socket.shutdownOutput();
        }
    }
}
