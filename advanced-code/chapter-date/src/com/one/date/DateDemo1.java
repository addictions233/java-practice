package com.one.date;

import java.util.Date;

/**
 *  java.util.Date是JDK提供给我们的操作时间的基本类
 *      构造方法: Date() 创建当前时间的date对象
 *               Date(long date)  创建给定时间戳的date对象
 *   Date类的过时方法
 *      String toLocalString() 以本地时区习惯的表示时间的方式展示时间
 *      String toGMTString() 以0时区习惯展示时间
 */
public class DateDemo1 {
    public static void main(String[] args) {
        Date date = new Date();
        // 输出: date:Wed Sep 23 09:18:36 CST 2020
        System.out.println("date:"+date);
        //输出: 2020年9月23日 上午9:24:59
        System.out.println(date.toLocaleString());
        System.out.println(date.toGMTString());
        long time = date.getTime();
        System.out.println(time);

        System.out.println("---------------------");
        // Thu Jan 01 08:00:00 CST 1970
        Date date1 = new Date(0L);
        System.out.println(date1);
        // Thu Jan 01 09:00:00 CST 1970
        System.out.println(new Date(60 * 60 * 1000));

    }
}
