package com.one.zone;

import java.time.Instant;
import java.time.LocalDateTime;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZoneDateTimeDemo4 {
    public static void main(String[] args) {
        long timestamp = System.currentTimeMillis();

        // 系统默认东八区
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        //ZoneDateTime 位移 到 UTC
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")); //ZoneDateTime 位移 到 UTC

        System.out.println("timestamp 转 ZoneDateTime:"+ zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); // 可給 JS 使用,2022-07-30T05:30:00Z
    }
}
