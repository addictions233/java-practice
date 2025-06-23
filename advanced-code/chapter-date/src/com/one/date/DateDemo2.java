package com.one.date;

import java.util.Date;

/**
 *  获取时间方法
 *      long getTime()  获取当前Date对象距离时间原点过了多少毫秒值
 *
 *  设置时间的方法: 将调用该方法的date对象的时间戳清0, 然后设置为指定的时间戳
 *      void setTime(long time) 参数是距离时间原点的时间毫秒值
 *
 */
public class DateDemo2 {
    public static void main(String[] args) {
        Date date = new Date(1000);
        System.out.println(date.getTime());
        // 获取当前时间的毫秒值
        System.out.println(System.currentTimeMillis());
        System.out.println("-------");
        date.setTime(0);
        System.out.println(date);
    }
}
