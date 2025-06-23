package com.one.changestream;

import java.io.*;

/**
 * @author one
 * 转换流是对字节流的封装,将字节流封装成字符流, 文件字符流FileReader和转换流 InputStreamReader都是封装字节流获取的字符流
 * FileReader继承自 InputStreamReader 继承自 Reader, 所以转换流对象算字符流
 *
 * 高效字符流 BufferedReader和BufferedWriter即可以通过文件字符流FileReader和FileWriter创建,也能通过转换流InputStreamReader
 * 和InputStreamWritter创建
 */
public class ChangeStreamDemo1 {

    public static void main(String[] args) throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("My_Day13\\转换流.txt"));
        // 注意区分这两种创建高效字符流的区别
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("My_Day12\\静夜思.txt")));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("My_Day13\\转换流.txt")));
        String line;
        while((line = br.readLine())!= null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        //关流 , 只用关闭最外面的流
        br.close();
        bw.close();
    }
}
