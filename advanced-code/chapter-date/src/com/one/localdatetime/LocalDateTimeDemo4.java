package com.one.localdatetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName: LocalDateTimeDemo4
 * @Description: 通过LocalDateTime类获取LocalDate类和LocalTime类
 *               LocalDateTime类的格式化器是DateTimeFormatter类
 * @Author: one
 * @Date: 2021/06/20
 */
public class LocalDateTimeDemo4 {
    public static void main(String[] args) {
        // 首先创建一个LocalDateTime对象
        LocalDateTime localDateTime = LocalDateTime.of(2020,11,11,14,20,30);
        // 获取LocalDate对象
        LocalDate localDate = localDateTime.toLocalDate();
        // 输出: 2020-11-11
        System.out.println(localDate);
        // 获取LocalTime对象
        LocalTime localTime = localDateTime.toLocalTime();
        // 输出: 14:20:30
        System.out.println(localTime);
        //直接输出localDateTime类: 2020-11-11T14:20:30, 如何按照我们想要的格式化输出呢?
        System.out.println(localDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String strLocalDateTime = formatter.format(localDateTime);
        // 输出: 2020年11月11日 14:20:30
        System.out.println(strLocalDateTime);
        // 另外一种方式: 使用LocalDateTime类中的format()方法 将LocalDateTime转换为字符串时间
        String format = localDateTime.format(formatter);
        // 同样输出: 2020年11月11日 14:20:30
        System.out.println(format);
    }
}
