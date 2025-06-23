package com.one.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 将学生对象信息写入到文件中
 */
public class IOTest1 {
    public static void main(String[] args) throws IOException {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("001","张三","23","男"));
        list.add(new Student("002","李四","24","男"));
        list.add(new Student("003","王五","25","男"));
        list.add(new Student("004","赵六","26","男"));
        //创建缓冲字符输出流
        BufferedWriter bw = new BufferedWriter(new FileWriter("My_Day12\\StudentInfro.txt"));
        //遍历集合,将集合中每个学生对象通过IO流写入到文件中
        for (Student student : list) {
            //因为字符串多次拼接操作会消耗很多内存,所以用字符缓冲区对象 StringBuilder
            StringBuilder sb = new StringBuilder();
            String string = sb.append(student.getId()).append(",").append(student.getName())
                    .append(",").append(student.getAge())
                    .append(",").append(student.getGender()).toString();
            bw.write(string);
            bw.newLine();
            bw.flush();
        }
        //关流
        bw.close();
    }
}
