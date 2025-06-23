package com.corejava.sort;

import javax.swing.*;
import java.util.Arrays;
import java.util.Comparator;
import java.awt.*;

/**
 *  用 lambda表达式重写实现比较器Comparator接口和监听器ActionListener接口的重写
 */
public class LambdaDemo {
    public static void main(String[] args) {
        //定义一个字符串数组
        String[] strings = {"peter","tom","smith","joy","alinda"};
        //排序前打印
        System.out.println(Arrays.toString(strings));  // 输出: [peter, tom, smith, joy, alinda]
        //对字符串数组进行排序
        /*
            Arrays.sort()方法是怎么识别后面传入的参数?
                1,首先sort(T[] a, Comparator<? super T> c)方法规定了后面传入的参数类型
                2,lambda表达式相当于匿名子类对象,所以可以传递其 父类对象Comparator<T> c接收

         */
        Arrays.sort(strings, (string1,string2)-> string1.length()-string2.length());
        //排序后打印
        System.out.println(Arrays.toString(strings));  // 输出 : [tom, joy, peter, smith, alinda]


        //重写监听器ActionListener接口
        int count = 0;
        Timer timer = new Timer(1000,(actionEvent)-> System.out.println("welcom to java world"));
        timer.start();

        JOptionPane.showMessageDialog(null,"quit program?");
        System.exit(0);  //系统正常退出


    }
}
