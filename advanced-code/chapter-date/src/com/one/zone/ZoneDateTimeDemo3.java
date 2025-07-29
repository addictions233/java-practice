package com.one.zone;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZoneDateTimeDemo3 {
    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis(); //2022-07-30T13:30:00.000+08:00
        System.out.println("API to JavaScript");
        /** Long 轉 ZoneDateTime */
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        System.out.println("Long 轉 ZoneDateTime:"+ ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        System.out.println("-----------------------------------");

        System.out.println("...以下可傳給 JavaScript");
        System.out.println("Long 轉 ZoneDateTime:"+ ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用,2022-07-30T13:30:00+08:00
        System.out.println("-----------------------------------");

        ZonedDateTime utcZone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")); //ZoneDateTime 位移 到 UTC
        System.out.println("...以下可傳給 JavaScript");
        System.out.println("Long 轉 ZoneDateTime:"+ utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用,2022-07-30T05:30:00Z
        System.out.println("-----------------------------------");


        System.out.println("系統預設時區 : " + ZoneId.systemDefault());
    }
}
