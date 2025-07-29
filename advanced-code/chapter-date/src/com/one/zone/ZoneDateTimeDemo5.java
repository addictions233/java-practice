package com.one.zone;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZoneDateTimeDemo5 {
    public static void main(String[] args) {
        /** ISO 8601 String 轉 ZoneDateTime */

        String strDateTime = "2022-07-28T05:59:59.000+08:00";  // UTC+8的字符串时间

        System.out.println("标准格式解析【不用formatter】 : " + strDateTime);

        System.out.println("String 转 ZonedDate: " + ZonedDateTime.parse(strDateTime));


        System.out.println("---------------------------------------------------------");

        strDateTime = "2022-07-28T05:59:59.000Z"; // UTC的字符串时间

        System.out.println("标准格式解析【不用formatter】 : " + strDateTime);

        System.out.println("String 转 ZonedDate: " + ZonedDateTime.parse(strDateTime));

        System.out.println("---------------------------------------------------------");

        strDateTime = "20220728T000000Z";

        System.out.println("自定义格式解析 'Z' : " + strDateTime);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssz");

        System.out.println("String 转 ZonedDate: " + ZonedDateTime.parse(strDateTime, formatter1));

        System.out.println("---------------------------------------------------------");

        strDateTime = "20220728T000000+0000";

        System.out.println("自定义格式解析 +0800 : " + strDateTime);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssZ");

        System.out.println("String 转 ZonedDate: " + ZonedDateTime.parse(strDateTime, formatter2));
    }
}
