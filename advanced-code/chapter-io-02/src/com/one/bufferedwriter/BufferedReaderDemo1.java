package com.one.bufferedwriter;

import java.io.*;

/**
 * @ClassName: BufferedReaderDemo1
 * @Description: 高效字符流一次读取一行
 * @Author: one
 * @Date: 2021/08/01
 */
public class BufferedReaderDemo1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("My_Day12/出师表.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day12/出师表3.txt"));

        // 高效字符流可以一次读取一整行文字,但是不读末尾的换行符
        String line;
        while ((line = br.readLine()) != null) {
            bw.write(line);
            // 添加换行符
            bw.newLine();
            // 刷新缓冲区
            bw.flush();
        }

        //关流
        br.close();
        bw.close();
    }
}
