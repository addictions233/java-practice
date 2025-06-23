package com.one.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 将文件中存储的Student信息读取出来,并按照该信息创建学生对象
 */
public class IOTest2 {
    public static void main(String[] args) throws IOException {
        //创建缓冲字符输入流对象
        BufferedReader br = new BufferedReader(new FileReader("My_Day12\\StudentInfro.txt"));
        //创建集合对象来存储学生对象
        ArrayList<Student> list = new ArrayList<>();
        String line;
        while((line = br.readLine())!= null){
            String[] split = line.split(",");
            list.add(new Student(split[0],split[1],split[2],split[3]));
        }
        //关流
        br.close();
        /**
         *  遍历集合,输出打印集合中存储的元素
         */
        for (Student student : list) {
            System.out.println("student = " + student);
        }
    }
}
