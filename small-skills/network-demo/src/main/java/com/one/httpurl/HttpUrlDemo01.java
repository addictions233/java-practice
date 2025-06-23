package com.one.httpurl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author one
 * 使用 jdk提供的 HttpURLConnection 向指定的url路径发送http请求,获取响应结果
 */
public class HttpUrlDemo01 {
    public static void main(String[] args) throws Exception {
        // 这是我临时建的web项目,去访问它的index.html界面
        String strUrl = "http://localhost:8080/index.html";
        // 解析字符串创建URL对象
        URL url = new URL(strUrl);
        //用TCP连接, 访问URL资源,获取到HttpURLConnection连接对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //通过HttpURLConnection连接对象,建立与URL网路资源的字符输入流
        InputStreamReader input = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
        // 字节流转换为字符流,然后封装成高效字符流
        BufferedReader br = new BufferedReader(input);
        StringBuffer sb = new StringBuffer();
        String line = "";
        //读取网页上每行数据
        while ((line = br.readLine()) != null) {
            // 将读取到的每行数组存储到字符串缓冲区对象中
            sb.append(line);
            //换行
            sb.append("\r\n");
        }
        //将网络URL资源读取到的网页在控制台进行打印输入
        String sbString = sb.toString();
        System.out.println("result... " + sbString);
    }
}
