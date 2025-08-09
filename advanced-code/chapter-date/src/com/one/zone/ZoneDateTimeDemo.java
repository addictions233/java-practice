package com.one.zone;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author one
 * ZoneDateTime类: 为了方便的创建任意时间的Date对象,我们可以使用ZoneDateTime类
 * UTC 格式或是說它是 ISO 8601格式，它被定義在 “RFC 3339” 文件中，
 * 主要是上述的文字格式中加入了 “時區” 概念，基本的格式長這樣：
 * 2020-12-26T12:38:00Z
 * T 就代表 full date-time
 * Z 就代表 UTC +0，格林威治時間
 * 如果要其他時區可以這樣寫：2022-07-25T23:59:59+08:00，代表 +8 時區或是台北，格林威治時間+8小時
 */
public class ZoneDateTimeDemo {

    public static void main(String[] args) {
        // Timestamp: 它表示「从 UTC+0 时区的 1970 年 1 月 1 日 0 时 0 分 0 秒开始，总共过了多少ms」
        long timestamp = System.currentTimeMillis();
        // 几乎相等, Date类型的本质上也是从 UTC+0 时区的 1970 年 1 月 1 日 0 时 0 分 0 秒开始，总共过了多少ms
        System.out.println(new Date().getTime() - timestamp);
        // 几乎相等, Instant也是表示时间戳, 也是从 UTC+0 时区的 1970 年 1 月 1 日 0 时 0 分 0 秒开始，总共过了多少ms
        Instant instant = ZonedDateTime.now().toInstant();
        System.out.println(instant.toEpochMilli() - timestamp);

        System.out.println("取出 timestamp:\t" + timestamp);

        System.out.println("timestamp 转 LocalDateTime");

        // 系统预设时区 : Asia/Shanghai
        System.out.println("系统预设时区 : " + ZoneId.systemDefault());

        // LocalDateTime 在Java 中表示一个不带有时区信息的日期和时间。它代表的是一个具体的时间点，但没有指定是哪个时区的时间。
        // 它适合用于处理不需要考虑时区差异的本地日期时间场景
        // 将时间戳转换为 LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        System.out.println("时区 - 日期时间格式如下:");

        // 2025-07-30T00:32:32.876+08:00[Asia/Shanghai]
        // 将 LocalDateTime 指定时区就可以转换为 ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()); // 需要指定时区
        System.out.println(zonedDateTime);

        // 2025-07-30T00:32:32.876Z[UTC] 指定为 UTC 时区
        System.out.println(localDateTime.atZone(ZoneId.of("UTC"))); //指定时区, ex: Asia/Shanghai

        // 2025-07-30T00:32:32.876
        System.out.println(localDateTime); //未指定时区 localDateTime 不包含时区信息

        // localDateTime 指定 ZoneId 后变成 ZoneDateTime 就可位移
        ZonedDateTime zonedSys = localDateTime.atZone(ZoneId.systemDefault());
        System.out.println("系统默认时区 = " + zonedSys);

        // 将东八区的ZonedDateTime 位移 到 UTC 後，觀察它們的小時部分，發現UTC 真的少了8小時
        ZonedDateTime utcZone = zonedSys.withZoneSameInstant(ZoneId.of("UTC")); //ZoneDateTime 位移 到 UTC
        System.out.println("位移 到 UTC = " + utcZone); // 位移 到 UTC = 2025-07-29T16:38:14.026Z[UTC]

        // 不管时区怎么转换, timestamp时间戳不会变, 都是表示从 UTC+0 时区的 1970 年 1 月 1 日 0 时 0 分 0 秒开始，总共过了多少ms
        System.out.println("位移 到 UTC, 转为 timestamp = " + zonedSys.toInstant().toEpochMilli()); // long
        System.out.println("位移 到 UTC, 转为 timestamp = " + utcZone.toInstant().toEpochMilli()); // long

        // 将ZonedDateTime 转换为 LocalDateTime
        LocalDateTime zonedSysLocalDateTime = zonedSys.toLocalDateTime();
        // 系统默认时区的 LocalDateTime = 2025-08-09T10:11:14.603
        System.out.println("系统默认时区的 LocalDateTime = " + zonedSysLocalDateTime);
        LocalDateTime utcZoneLocalDateTime = utcZone.toLocalDateTime();
        // UTC 时区的 LocalDateTime = 2025-08-09T02:11:14.603
        System.out.println("UTC 时区的 LocalDateTime = " + utcZoneLocalDateTime);
    }
}
