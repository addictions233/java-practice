package com.one.systemin_systemout;

import java.io.*;


public class SystemInDemo2 {
    public static void main(String[] args) throws IOException {
        /**
         *  InputStream in = System.in;   //标准的键盘输入流
         *  通常键盘录入的都是字符串,
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day13\\标准键盘输入流.txt"));
        String line;
        // readLine()方法一次读取一行,但不读换行符
        while(!(line = br.readLine()).equals("end")){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        bw.close();
        br.close();


    }
}
