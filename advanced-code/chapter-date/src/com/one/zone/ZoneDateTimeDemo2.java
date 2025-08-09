package com.one.zone;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ZoneDateTimeDemo2 {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime localDateTime = LocalDateTime.parse("2019-07-31 00:00:00", dateTimeFormatter1);

        LocalDate localDate = LocalDate.parse("2019-07-31", dateTimeFormatter2);

        Date date = Date.from(LocalDateTime.parse("2019-07-31 00:00:00", dateTimeFormatter1).atZone(ZoneId.systemDefault()).toInstant());

        String strDateTime = "2019-07-31 00:00:00";

        String strDate = "2019-07-31";

        Long timestamp = 1564502400000L;


        /** LocalDateTime 转 LocalDate */
        System.out.println("LocalDateTime 转 LocalDate: " + localDateTime.toLocalDate());

        /** LocalDateTime 转 Long */
        System.out.println("LocalDateTime 转 Long: " + localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        /** LocalDateTime 转 Date */
        System.out.println("LocalDateTime 转 Date: " + Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));

        /** LocalDateTime 转 String */
        System.out.println("LocalDateTime 转 String: " + localDateTime.format(dateTimeFormatter1));


        System.out.println("-------------------------------");


        /** LocalDate 转 LocalDateTime */
        System.out.println("LocalDate 转 LocalDateTime: " + LocalDateTime.of(localDate, LocalTime.parse("00:00:00")));

        /** LocalDate 转 Long */
        System.out.println("LocalDate 转 Long: " + localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());

        /** LocalDate 转 Date */
        System.out.println("LocalDate 转 Date: " + Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        /** LocalDate 转 String */
        System.out.println("LocalDateTime 转 String: " + localDateTime.format(dateTimeFormatter2));


        System.out.println("-------------------------------");


        /** Date 转 LocalDateTime */
        System.out.println("Date 转 LocalDateTime: " + LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));

        /** Date 转 Long */
        System.out.println("Date 转 Long: " + date.getTime());

        /** Date 转 LocalDate */
        System.out.println("Date 转 LocalDateTime: " + LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate());

        /** Date 转 String */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

        System.out.println("Date 转 String: " + sdf.format(date));


        System.out.println("-------------------------------");


        /** String 转 LocalDateTime */
        System.out.println("String 转 LocalDateTime: " + LocalDateTime.parse(strDateTime, dateTimeFormatter1));

        /** String 转 LocalDate */
        System.out.println("String 转 LocalDate: " + LocalDateTime.parse(strDateTime, dateTimeFormatter1).toLocalDate());

        System.out.println("String 转 LocalDate: " + LocalDate.parse(strDate, dateTimeFormatter2));

        /** String 转 Date */
        System.out.println("String 转 Date: " + Date.from(LocalDateTime.parse(strDateTime, dateTimeFormatter1).atZone(ZoneId.systemDefault()).toInstant()));


        System.out.println("-------------------------------");


        /** Long 转 LocalDateTime */
        System.out.println("Long 转 LocalDateTime:" + LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));

        /** Long 转 LocalDate */
        System.out.println("Long 转 LocalDate:" + LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toLocalDate());

        /** Long 转 ZoneDateTime */
        localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        System.out.println("Long 转 ZoneDateTime:" + ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用

        System.out.println("Long 转 ZoneDateTime:" + ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        ZonedDateTime utcZone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")); //ZoneDateTime 位移 到 UTC

        System.out.println("Long 转 ZoneDateTime:" + utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用

        System.out.println("Long 转 ZoneDateTime:" + utcZone.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));


        System.out.println("-------------------------------");

        System.out.println("ms = 123");

        timestamp += 123;

        localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        System.out.println("Long 转 ZoneDateTime:" + ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用

        System.out.println("Long 转 ZoneDateTime:" + ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        utcZone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")); //ZoneDateTime 位移 到 UTC

        System.out.println("Long 转 ZoneDateTime:" + utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用

        System.out.println("Long 转 ZoneDateTime:" + utcZone.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));

        System.out.println("-------------------------------");

        System.out.println("Long 转 ZoneDateTime (JS可用) :" + ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用

        System.out.println("Long 转 ZoneDateTime (JS可用) :" + utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));// 可給 JS 使用


        System.out.println("-------------------------------");




    }
}
