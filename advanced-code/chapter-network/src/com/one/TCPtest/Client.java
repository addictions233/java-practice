package com.one.TCPtest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        //创建键盘录入对象
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String uName = sc.nextLine();
        System.out.println("请输入密码:");
        String uPassworld = sc.nextLine();
        String client = uName+","+uPassworld;
        InetAddress adress = InetAddress.getByName("192.168.67.207");
        Socket socket = new Socket(adress,10010);
        OutputStream os = socket.getOutputStream();
        os.write(client.getBytes());

        InputStream inputStream = socket.getInputStream();
        byte[] bys = new byte[1024];
        int len = inputStream.read(bys);
        System.out.println(new String(bys,0,len));
    }
}
