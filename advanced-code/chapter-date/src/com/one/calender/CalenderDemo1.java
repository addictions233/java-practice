package com.one.calender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: CalenderDemo1
 * @Description: 日历时间: 使用
 * @Author: one
 * @Date: 2021/06/20
 */
public class CalenderDemo1 {
    public static void main(String[] args) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        // 通过Calender类获取Date对象
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = simpleDateFormat.format(date);
        System.out.println(strDate);

        // 通过Date对象获取Calender对象
        Date date2 = simpleDateFormat.parse("2010-11-11 23:59:59");
        calendar.setTime(date2);
        Date date3 = calendar.getTime();
        System.out.println(simpleDateFormat.format(date3));

        // Calender类的一些常用方法
        System.out.println(calendar.getTimeZone());
        System.out.println(calendar.getWeeksInWeekYear());
    }
}
