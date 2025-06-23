package com.one.localdatetime;

import java.time.LocalDateTime;

/**
 * @author one
 *  JDK8将日期时间类Date 分为了下面三种:
 *   Date类拆分为了下面三种:
 *      LocalDateTime : 日期时间类
 *      LocalDate : 日期类
 *      LocalTime: 时间类
 *
 */
public class LocalDateTimeDemo1 {
    public static void main(String[] args) {
        // 获取当前时间的LocalDateTime类对象, 相当于 new Date()
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        // 获取指定时间的LocalDateTime类对象, 相当于 new Date(Long timeMills)
        LocalDateTime ldt2 = LocalDateTime.of(2020,11,11,6,6,6);
        System.out.println(ldt2);
    }
}
