package com.one.printstream;
import java.io.*;
/**
 * PrintWriter：打印字符输出流
 *   自动刷新：
 *   	1、必须在创建流对象的时候开启自动刷新的开关
 *   	2、必须使用println、printf、format方法之一
 *   自动换行：
 *   	1、必须使用println方法
 *  	PrintWriter是Writer的子类,所以它具备5种写的方法：write
 *   	1、一次一个字符
 *   	2、一次一个字符数组
 *   	3、一次一个字符数组的一部分
 *   	4、一次一个字符串
 *   	5、一次一个字符串的一部分
 *   在使用PrintWriter对象的时候基本上使用的都是 println.
 */
public class PrintWriterDemo1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("My_Day13\\转换流.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("My_Day13\\转换流copy.txt"),true);
        String line;
        while((line = br.readLine())!= null){
//            pw.write(line);
//            pw.write("\r\n");
//            pw.flush();
              pw.println(line);  // 用println()方法自动换行
        }

        br.close();
        pw.close();
    }
}
