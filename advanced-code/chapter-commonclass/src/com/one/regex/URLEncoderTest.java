package com.one.regex;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @ClassName: URLEncoderTest
 * @Description: TODO
 * @Author: one
 * @Date: 2021/08/31
 */
public class URLEncoderTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "a b";
        String encode = URLEncoder.encode(str, "UTF-8");
        System.out.println(encode);
        str = str.replaceAll(" ","%20");
//        String encode1 = URLEncoder.encode(str, "UTF-8");
        String decode = URLDecoder.decode(str, "UTF-8");
        System.out.println(decode);
    }
}
