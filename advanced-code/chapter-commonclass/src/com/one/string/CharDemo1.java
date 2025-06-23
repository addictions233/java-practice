package com.one.string;


/**
 * @PackageName: com.corejava.chardemo
 * @ClassName: CharDemo1
 * @Description: 学习数据类型char
 * @Author: one
 * @Date: 2020-9-4 20:29
 */
public class CharDemo1 {
    public static void main(String[] args) {  // \u005B\u005D 表示 []
        /**
         * char类型的几种表现形式:
         *  1,写在单引号里面的单个字符 例如: 'A';
         *  2,char类型的值可以表示为十六进制,其范围从\u0000到\uFFFF,也就是十进制的0到65535
         *      注意: \\u是转义序列(强烈提醒:代码注释中不要出现\\u)
         *            一个十六进制数可以用四个二进制数表示 (F)十六进制 = (1111)二进制 = (15)十进制
         */
         char a1 = '\u0061';
         System.out.println(a1);  //输出: a
         char a2 = 97;
         System.out.println(a2);  //输出: a
         char a3 = 'a';
         System.out.println(a3);  //输出: a
         System.out.println("\u0061");  //输出 : a
         System.out.println("java\u0061");  //输出: javaa


    }
}
