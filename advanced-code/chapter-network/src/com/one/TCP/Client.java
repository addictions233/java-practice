package com.one.TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *  模拟客户端
 */
public class Client {
    public static void main(String[] args) throws IOException {
       //1,创建TCP发送端对象
        Socket socket = new Socket("127.0.0.1",10086);
        OutputStream os = socket.getOutputStream();
        os.write("今天中午好热".getBytes());

        //显示服务器的回显信息,需要接受数据
        InputStream in = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = in.read(bytes);
        String string = new String(bytes, 0, len);
        System.out.println("string = " + string);
        //关流
        os.close();
    }
}
