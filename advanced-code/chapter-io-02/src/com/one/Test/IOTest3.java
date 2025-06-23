package com.one.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *  将出师表.txt中打乱的语句按照正确方式拍好序
 */
public class IOTest3 {
    public static void main(String[] args) throws IOException {
        // 创建缓冲字符输入流对象读取目标文件
        BufferedReader br = new BufferedReader(new FileReader("My_Day12\\出师表.txt"));
        //创建集合对象,用来存储缓冲字符流读取的字符串
        ArrayList<String> list = new ArrayList<>();
        String line;
        while((line = br.readLine())!= null){
            //将读取到的整行字符串存储到集合中
            list.add(line);
        }
        br.close();
        //创建缓冲字符输出流对象,将集合中的元素写入到目标文件中
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day12\\出师表.txt"));
        /**
         * 能对list集合中的元素进行排序的前提是list集合中的元素实现了Comparable接口
         */
        Collections.sort(list);
        for (String str : list) {
            bw.write(str);
            bw.newLine(); //换行
            bw.flush();
        }
        bw.close();
    }
}
