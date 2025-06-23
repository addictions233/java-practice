package com.one.TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  模拟服务器端
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(10086);

        Socket accept = ss.accept();

        InputStream is = accept.getInputStream();

        byte[] bys = new byte[1024];
        int len;
        len = is.read(bys);
        String line = new String(bys,0,len);
        System.out.println("line = " + line);

        OutputStream os = accept.getOutputStream();
        os.write("服务器收到了你的消息".getBytes());

        //关流
        os.close();
        accept.close();

    }
}
