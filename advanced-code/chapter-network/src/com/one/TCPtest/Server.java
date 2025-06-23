package com.one.TCPtest;

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
        //模拟数据库
        String user = "admin,12345";
        //用TCP进行通讯
        ServerSocket ss = new ServerSocket(10010);

        //阻塞
        Socket accept = ss.accept();

        //创建字节输入流
        InputStream is = accept.getInputStream();

        //接受客户端的信息
        byte[] bys = new byte[1024];
        int len = is.read(bys);

        //对客户端输入的登陆进行校验
        String client = new String(bys,0,len);

        //创建服务器端的回写流
        OutputStream os = accept.getOutputStream();

        if(user.equals(client)){
            os.write("登陆成功!".getBytes());
        } else {
            os.write("账号和密码有误,登陆失败!".getBytes());
        }

//        os.close();
//        accept.close();
    }
}
