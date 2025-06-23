package com.one.string;

/**
 * @PackageName: com.corejava.string
 * @ClassName: StringDemo3
 * @Description: 空串和 null串
 * @Author: one
 * @Date: 2020-9-5 20:16
 */
public class StringDemo3 {
    public static void main(String[] args) {
        /**
         * 空字符串 : 空字符串是一个java对象,有自己的串长度(0) 和 内容为空
         *      即   变量名.length() = 0 或 变量名.equals("") 返回true
         */
        String str1 ="";
        String str2 = " "; //双引号里面的空格符号也是一个字符 ,所以不是空字符串
        System.out.println(str1.length()); //输出: 0
        System.out.println(str2.length()); //输出: 1

        /*
            检测一个字符串既不是null也不是空字符串,用下面表达式判断
         */
        String str = " "; //声明字符串 str
        boolean b = (str != null) && (str.length() != 0);
        System.out.println(b);
    }
}
