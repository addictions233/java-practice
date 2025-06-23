package com.one.date;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author one
 *  Date类
 *  jdk8之前的时间类
 *      创建Date类对象的三种方式:
 *          1, new Date() 当前时间对象
 *          2, new Date( Long timemills) 通过时间毫秒值创建Date对象
 *          3, simpleDateForMate.porse(String date) 将字符串时间转换为Date对象
 *          4, calender.getTime() 通过日历对象获取时间对象
 */
public class DateDemo3 {
    public static void main(String[] args) throws ParseException {
        // date类构造方法
        Date date1 = new Date();
        System.out.println(date1); // 输出: Wed Sep 23 17:49:37 CST 2020
        Date date2 = new Date((1000L*60*60*24));
        System.out.println(date2);// 输出: Fri Jan 02 08:00:00 CST 1970

        // SimpleDateFormat类 : 时间格式类
        String string = "yyyy年MM月dd日 HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(string);
        Date date3 = dateFormat.parse("2000年01月01日 08:08:08");
        System.out.println(date3); // 输出: Sat Jan 01 08:08:08 CST 2000

        //获取Calender日历对象
        Calendar calendar = Calendar.getInstance();
        // 通过calender类中的getTime()方法,获取Date对象
        Date date4 = calendar.getTime();
        System.out.println(calendar);
        System.out.println(date4);

        // 将Date对象转换为Calender对象
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date3);
        System.out.println(calendar1);
    }
}
