package com.one.httpurl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author one
 * 使用HttpURLConnection测试获取指定的url路径中的网页资源
 */
public class HttpUrlDemo02 {
    public static void main(String[] args) {
        String url = "http://localhost:8080/index.html";
        String param = null;
        String result = sendGet(url, param);
        // 调用write()方法,将服务器端响应的结果写入到aaa.txt文件中
        write("E:\\aaa.txt",result);

    }

    /**
     * 向某个网页发送http请求
     * @param url  请求的url路径
     * @param param 请求行中的参数
     * @return
     */
    public static String sendGet (String url,String param){
        // 定义一个空字符串,用来接收服务器端返回的结果
        String result ="";
        if(param != null){
            url = url +"?" + param;
        }

        try{
            // 创建http请求的url对象
            URL realUrl = new URL(url);
            // 调用getHttpURLConnection()方法,建立http请求连接
            HttpURLConnection connection = getHttpURLConnection(realUrl);
            //调用printHeader()方法,打印响应头中的信息
            printHeader(connection);
            //调用getResponse()方法,获取服务器端的响应,结果为String类型
            result = getResponse(connection);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置http请求的请求行和请求头信息,建立连接
     * @param realUrl
     * @return
     */
    public static HttpURLConnection getHttpURLConnection (URL realUrl){
        StringBuilder sb = new StringBuilder();
        sb.append("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        sb.append("AppleWrbKit/537.36(KHTML, like Gecko)");
        sb.append("Chrome/72.0.3626.119 Safari/537.36");
        HttpURLConnection connection = null;
        try {
            //打开与URL之间的连接,获取HttpURLConnection连接对象
            connection = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性, 即请求报文需求
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     *  获取http请求的响应结果,并将响应结果转化为String字符串
     * @param connection
     * @return
     */
    private static String getResponse(HttpURLConnection connection) {
        // 读取URL的响应
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while((line = br.readLine())!=null){
                result.append(line).append("\r\n");
            }
        } catch (IOException exception){
            exception.printStackTrace();
        } finally {
            connection.disconnect();
        }
        System.out.println("getResponse:"+result.length());
        return result.toString();
    }

    /**
     * 在控制台打印响应头中的信息,
     * @param connection
     */
    private static void printHeader(HttpURLConnection connection) {
        System.out.println("----响应头中的信息:----");
        Map<String, List<String>> fields = connection.getHeaderFields();
        for(String str: fields.keySet()){
            StringBuilder sb = new StringBuilder(str+":");
            List<String> strings = fields.get(str);
            for (String string : strings) {
                sb.append(string+",");
            }
            System.out.println(sb.toString());
        }
        System.out.println("----------------------------");
    }

    /**
     * 将text字符串中的信息写到file文件中
     * @param fileName
     * @param text
     */
    private static void write(String fileName, String text) {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
             BufferedWriter bw = new BufferedWriter(osw);) {
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
