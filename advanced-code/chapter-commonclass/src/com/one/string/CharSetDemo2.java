package com.one.string;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @ClassName: CharStreamDemo1
 * @Description: 字符串的编码和解码问题:
 *          1, 编码:  byte[] getBytes() 使用平台默认的字符集将String编码为一个字节数组,
 *                    byte[] getBytes(String charsetName) 使用指定的字符集将该String编码为一个字节数组
 *          2, 解码: String(byte[] bytes) 通过平台默认的字符集将指定的字节数组转换为String
 *                  String(byte[], String charsetName) 通过指定的字符集将指定的字节数组来构建新的String
 * @Author: one
 * @Date: 2021/07/29
 */
public class CharSetDemo2 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "我爱你中国";
        byte[] bytes = str.getBytes();
        // 平台默认的字符集是 UTF-8, 在UTF-8编码格式下,一个汉字占用三个字节
        // 输出:[-26, -120, -111, -25, -120, -79, -28, -67, -96, -28, -72, -83, -27, -101, -67]
        System.out.println(Arrays.toString(bytes));
        // 使用指定的字符集 GBK将字符串解析为字节数组,在GBK编码格式下,一个中文汉字占两个字节
        byte[] gbks = str.getBytes("GBK");
        // 输出: [-50, -46, -80, -82, -60, -29, -42, -48, -71, -6]
        System.out.println(Arrays.toString(gbks));

        // 使用平台默认的字符集对字节数组解析解码
        String parseStr = new String (bytes);
        // 输出: 我爱你中国
        System.out.println(parseStr);
        // 使用指定的字符集对象字节数组进行解码
        String parseStr2 = new String(bytes,"GBK");
        // j将UTF-8编码的字符串用GBK来解码会出现乱码
        System.out.println(parseStr2);
        String parserStr3 = new String(gbks,"GBK");
        System.out.println(parserStr3);
    }
}
