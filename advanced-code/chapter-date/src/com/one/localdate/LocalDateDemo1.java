package com.one.localdate;

import java.time.LocalDate;

/**
 * java类库中的 LocalDate类  --> 用日历表示法来表示日期   年月日
 * 1,不要用构造器来创建LocalDate类实例对象,即用 new关键字
 * 2,创建 LocalDate类对象用静态工厂方法
 */
public class LocalDateDemo1 {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now);  // 2020-09-09  年-月-日
        int year = now.getYear();  //获取年份
        int month = now.getMonthValue();  // 获取月份
        int day = now.getDayOfMonth();  //获取日期
        System.out.println("year = " + year);
        System.out.println("month = " + month);
        System.out.println("day = " + day);

        LocalDate threeHundredSixtyFiveDaysLater = now.plusDays(365); //365天之后的日期
        System.out.println(threeHundredSixtyFiveDaysLater.getYear());
        System.out.println(threeHundredSixtyFiveDaysLater.getMonthValue());
        System.out.println(threeHundredSixtyFiveDaysLater.getDayOfMonth());

    }
}




