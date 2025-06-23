package com.one.localdatetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author one
 */
public class LocalDateTimeDemo2 {
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.now();
        System.out.println(date); // 2020-09-23T14:37:52.263635

        LocalDateTime date2 = LocalDateTime.of(1990,1,1,12,0,0);

        // 区别于 SimpleDateFormat类
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //将date转换为for
        String formatterTime = date.format(formatter);
        System.out.println(formatterTime);
        String formatTime1 = date2.format(formatter);
        System.out.println(formatTime1);

        String strDate = "1959-01-01 09:08:12";
        LocalDateTime parse = LocalDateTime.parse(strDate, formatter);
        System.out.println(parse);

    }
}
