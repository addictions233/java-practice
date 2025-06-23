package com.one.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CharSetDemo
 * @Description: 对 Unicode字符集的代码演示 java中的char代表一个Code Unit编码单元
 *               而一个字符 (Code Point编码点)是由一个Code Unit或者两个 Code Unit
 *               在java9中，出于空间优化的目的，String的内部实现已经由char[]调整为byte[]
 * @Author: one
 * @Date: 2021/07/31
 */
public class CharSetDemo1 {
    public static void main(String[] args) throws Exception{
        String str = "我爱你中国";
        // java编程语言的内码编码是utf-16,BMP范围内的字符占一个code unit编码单元,即一个char 两个字节
        // String类中的length()返回的是code unit编码单元的个数，而不是字符的个数(特殊字符占两个编码单元)，也不是占用字节的个数
        // 输出: 5
        System.out.println(str.length());

        //统计String中的字符(code point编码点)的个数，请使用String.codePointCount()方法
        System.out.println(str.codePointCount(0,str.length()));

        //String.charAt()参数索引指的是code unit的索引，返回值是char类型
        System.out.println(str.charAt(4));

        byte[] bytes = str.getBytes("utf-16");
        // utf-16编码格式下输出: 12 一个汉字占2个字节
        System.out.println(bytes.length);
        byte[] bys = str.getBytes("utf-8");
        // uft-8编码格式下输出: 15  一个汉字占3个字节
        System.out.println(bys.length);

        List<Integer> codePoints = stringToCodePoints(str);
        for (int codePoint : codePoints) {
            // 其实不能这个强转,有的code point必须用两个char表示
            System.out.print((char)codePoint);
        }
    }

    /**
     * 统计一个字符串中所用的code point编码点(字符)
     * @param str 输入的字符串
     * @return List<Integer> 所用的code point组成的List集合
     */
    public static List<Integer> stringToCodePoints(String str) {
        List<Integer> codePoints = new ArrayList<>();
        // code unit编码单元的个数
        final int length = str.length();
        for (int offset = 0; offset < length; ) {
            int codePoint = str.codePointAt(offset);
            codePoints.add(codePoint);
            // charCount()方法是看一个code point是由一个code unit组成还是两个code unit组成
            offset += Character.charCount(codePoint);
        }
        return codePoints;
    }
}
