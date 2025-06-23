package com.one.bufferedwriter;

import java.io.*;

public class BufferedWriterDemo1 {
    public static void main(String[] args) throws IOException {
        // 通过包装字符流对象创建高效字符流对象
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day12\\诗歌复制.txt"));
        BufferedReader br = new BufferedReader(new FileReader("My_Day12\\静夜思.txt"));
        String line;
        while((line = br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        br.close();
        bw.close();
    }
}
