package com.one.localdatetime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * @ClassName: LocalDateTimeDemo5
 * @Description: 使用时间间隔对象: Duration类和 Period类
 * @Author: one
 * @Date: 2021/06/20
 */
public class LocalDateTimeDemo5 {
    public static void main(String[] args) {
        /**
         * Period类是日期间隔对象 创建对象的方法: static Period between(LocalDate start, LocalDate end)
         */
        LocalDate localDate1 = LocalDate.of(2020,1,1);
        LocalDate localDate2 = LocalDate.of(2020,3,2);
        Period period = Period.between(localDate1, localDate2);
        // 获取间隔的年: 0
        System.out.println(period.getYears());
        // 获取间隔的月: 2
        System.out.println(period.getMonths());
        // 获取间隔的天: 1
        System.out.println(period.getDays());

        /**
         * Duration类是时间间隔对象: 创建对象的方法: static Duration between(Temporal start,Temporal end)
         * Temporal是LocalDateTime, LocalDate, LocalTime等类的父接口
         */
        LocalDateTime localDateTime1 = LocalDateTime.of(2020,11,11,13,38,51);
        LocalDateTime localDateTime2 = LocalDateTime.of(2020,12,13,23,38,55);
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        // 输出: 32
        System.out.println(duration.toDays());
    }
}
