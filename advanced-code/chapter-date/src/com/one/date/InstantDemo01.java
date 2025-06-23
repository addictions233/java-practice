package com.one.date;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/*
    计算时长的三种方式: Instant System Date 三个类
    Instant 表示时间点
        静态方法 Instant.now()获取当前时间点
    静态方法 Duration.between()方法获取两个时刻之间的时间差, 返回Duration对象

 */
public class InstantDemo01 {
    public static void main(String[] args) throws InterruptedException {
        /*
            Instant类和Duration类
         */
        Instant start = Instant.now();
        Thread.currentThread().sleep(1000);
        Instant end = Instant.now();
        // 输出: end:2020-11-21T12:25:20.959084900Z  时刻点
        System.out.println("end:"+ end);
        Duration between = Duration.between(start, end);
        long millis = between.toMillis();
        System.out.println(millis);

        /*
            静态方法 System.currentTimeMills()方法获取当前时刻距离时间原点的毫米值
         */
        System.out.println("====================");
        long currentTimeMillis1 = System.currentTimeMillis();
        Thread.currentThread().sleep(1000);
        long currentTimeMillis2 = System.currentTimeMillis();
        // 输出: currentTimeMillis2 = 1605961521979 时间毫秒值
        System.out.println("currentTimeMillis2 = " + currentTimeMillis2);
        System.out.println(currentTimeMillis2-currentTimeMillis1);


        /*
            Date类的空参构造
                Date类空参构造获取当前时间
                Date类对象调用getTime()方法返回Date对象距离时间原点的毫秒值
         */
        System.out.println("============");
        Date date1 = new Date();
        Thread.currentThread().sleep(1000);
        Date date2 = new Date();
        // 输出: date1:Sat Nov 21 20:25:21 CST 2020
        System.out.println("date1:"+date1);
        System.out.println(date2.getTime()-date1.getTime());
    }
}
