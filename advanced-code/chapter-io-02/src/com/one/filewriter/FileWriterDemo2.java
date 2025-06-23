package com.one.filewriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterDemo2 {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("My_Day12\\src\\com\\itheima\\filewriter\\FileWriterDemo1.java");
        FileWriter fw = new FileWriter("My_Day12\\eee.txt");
        char[] chars = new char[1024];
        int lenth;
        while((lenth = fr.read(chars))!=-1){
            fw.write(chars,0,lenth);
            fw.flush();
        }
        fr.close();
        fw.close();
    }
}
