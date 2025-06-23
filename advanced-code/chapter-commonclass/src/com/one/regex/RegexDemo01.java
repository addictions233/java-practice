package com.one.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: RegexDemo01
 * @Description: 正则表达式语法: 正则表达式的元字符大致可以分为:
 *  1, 限定符
 *  2, 选择匹配符
 *  3, 分组组合和反向引用符
 *  4, 特殊字符: 在正则表达式中特殊字符需要转义转义之后,才能表达特殊字符它本身 ^ $ * . + ? |
 *  5, 字符匹配符 [] [^] - . \d
 *  6, 定位符
 * @Author: one
 * @Date: 2021/08/02
 */
public class RegexDemo01 {
    public static void main(String[] args) {
        String content = "abc(efg(m.";
        // '('在正则表达式中是特殊字符,需要通过转义字符转义之后来表示特殊符号本身
        // 在java语言中转义字符是'\'反斜杠,如果我们想在字符串中使用普通的'\'反斜杠时，就需要使用双反斜杠'\\'来表示
        // java语言需要"\\"双反斜杠来表示正则表达式中的普通反斜杠
        String regex = "\\(";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while(matcher.find()) {
            System.out.println("找到了:" + matcher.group(0));
        }
    }
}
