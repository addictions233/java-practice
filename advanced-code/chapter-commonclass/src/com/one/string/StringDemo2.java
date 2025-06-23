package com.one.string;

/**
 * @PackageName: com.corejava.string
 * @ClassName: StringDemo2
 * @Description: 获得子串方法 subString , 字符串的拼接
 * @Author: one
 * @Date: 2020-9-4 21:18
 */
public class StringDemo2 {
    public static void main(String[] args) {
        String string = "美好新中国";
        /*
             String substring(int beginIndex, int endIndex)  --> String 类中的非静态方法
                获得子串, 子串是从 beginIndex 一直到 enIndex-1 的字符组成的字符串,即不包括endIndex
                所以子串的字符个数就是 endIndex - beginIndex
         */
        String  str = string.substring(3,5);
        System.out.println(str);

        /*
            static String join(CharSequence delimiter, CharSequence... elements)    -->String类中的静态方法
                作用:将多个字符串拼接在一起,中间用界定符连接
                 第一个参数为界定符,后面的为被拼接的字符串
         */
        String join = String.join("\\", "a","b", "c", "d");
        System.out.println(join);  //输出 : a\b\c\d

        /*
            String	repeat​(int count) --> jdk 11 提供的非静态方法
                将字符串重复count遍并将结果返回
         */
//        String repeat = string.repeat(3);  //这个jdk还无法编译
//        System.out.println(repeat);

        /*
            通过提取字符串和拼接字符串可以对字符串进行修改,但是这样效率很低,没有重新定义一个字符串效率高
                例如: 将字符串 "美好新中国" 改为 "美好新世界"
         */
         string = string.substring(0,2);
         string = string + "新世界";
        System.out.println("string = " + string);  //注意: 这样做的效率比不上直接将写出 string = "美丽新世界";

    }
}
