package com.one.regex;

/**
 * @ClassName: StringSplit
 * @Description: String.split()方法
 * @Author: one
 * @Date: 2021/12/05
 */
public class StringSplit {
    public static void main(String[] args) {
        String source = "-3.31m1A";
        String numberRegex = "(\\d+(\\.\\d+)?)";
        // (?<=\\d)表示数字之后, (?=[a-zA-Z])表示字母之前,所以按照数字和字母的中间进行拆分
        String regex = "(?<=\\d)(?=[a-zA-Z])";
        String[] split = source.split(regex, 2);
        for (String s : split) {
            System.out.println(s);
        }
    }
}
