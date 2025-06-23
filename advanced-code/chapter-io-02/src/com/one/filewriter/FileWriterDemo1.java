package com.one.filewriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author one
 * 字符流的常用类:
 *          FileReader 继承自 InputStreamReader(转换流) 继承自 Reader(字符流的根抽象类)
 *          FileWriter 继承自 OutputStreamWriter(转换流) 继承自 Writer(字符流的根抽象类)
 */
public class FileWriterDemo1 {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("My_Day12\\src\\com\\itheima\\fileoutputstream\\FileOutputStream1.java");
        FileWriter fw = new FileWriter("My_Day12\\ddd.txt");

        int ch;
        while((ch=fr.read())!=-1){
            fw.write(ch);
            fw.flush();
        }
        fr.close();
        fw.close();
    }
}
