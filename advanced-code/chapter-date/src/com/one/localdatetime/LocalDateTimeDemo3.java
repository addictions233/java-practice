package com.one.localdatetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author one
 * 案例需求: 定义一个时间, String start = "2020年11月11日 00:00:00"
 * 将这个时间 +1 天, 再按照原来的格式进行输出
 */
public class LocalDateTimeDemo3 {
    public static void main(String[] args) throws ParseException {
//        method();
        /**
         * 使用jdk8中提供的日期时间类实现上述案例需求
         */
        String start = "2020年11月11日 00:00:00";
        // 创建日期时间格式换对象
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        // 将字符串时间转换为对应的LocalDateTime对象
        LocalDateTime localDateTime = LocalDateTime.parse(start, dateTimeFormatter);
        // 将localDateTime对象加上一天
        LocalDateTime newLocalDateTime = localDateTime.plusDays(1);
        // 新的localDateTime对象转换为字符串时间
        String end = newLocalDateTime.format(dateTimeFormatter);
        System.out.println(end);

    }

    /**
     * 使用jdk8之前的日期时间类实现上述案例需求
     * @throws ParseException
     */
    private static void method() throws ParseException {
        // 第一中方式: 我们使用Date类来进行实现
        String start = "2020年11月11日 00:00:00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = simpleDateFormat.parse(start);
        // 获取时间毫秒值
        long time = date.getTime();
        time = time + 24*60*60*1000;
        //将时间毫秒值转换为Date对象
        Date date2 = new Date(time);
        // 将date对象转换为字符串时间
        String end = simpleDateFormat.format(date2);
        System.out.println(end);
    }
}
