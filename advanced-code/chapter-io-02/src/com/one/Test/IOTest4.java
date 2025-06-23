package com.one.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  将出师表.txt中打乱的语句按照正确方式拍好序
 */
public class IOTest4 {
    public static void main(String[] args) throws IOException {
        //创建缓冲字节输入流对象
        BufferedReader br = new BufferedReader(new FileReader("My_Day12\\出师表.txt"));
        //创建List集合用来存储读取的字符串
        ArrayList<String> list  = new ArrayList<>();
        String line;
        while((line = br.readLine())!=null){
            list.add(line);
        }
        //关闭输入流
        br.close();
        //创建缓冲字符输入流对象
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day12\\出师表.txt"));
        //"."在正则表达式中是通配符,所以得用转义字符"\\."
        Collections.sort(list,(str1,str2)->Integer.parseInt(str1.split("\\.")[0])-Integer.parseInt(str2.split("\\.")[0]));
        for (String str : list) {
            bw.write(str);
            bw.newLine();
            bw.flush();
        }
        //关闭输出流
        bw.close();
    }
}
