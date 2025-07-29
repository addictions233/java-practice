package com.one.zone;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
        // Timestamp: 它表示「從 UTC+0 時區的 1970 年 1 月 1 號 0 時 0 分 0 秒開始，總共過了多少ms」
        long timestamp = System.currentTimeMillis();  // 我的電腦現是 2022-07-28 17:03:54

        System.out.println("取出 timestamp:\t" + timestamp);

        System.out.println("timestamp 转 LocalDateTime");

        // 系统预设时区 : Asia/Shanghai
        System.out.println("系统预设时区 : " + ZoneId.systemDefault());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        System.out.println("时区 - 日期时间格式如下:");

        // 2025-07-30T00:32:32.876+08:00[Asia/Shanghai]
        System.out.println(localDateTime.atZone(ZoneId.systemDefault()));  //記得指定时区

        // 2025-07-30T00:32:32.876Z[UTC]
        System.out.println(localDateTime.atZone(ZoneId.of("UTC"))); //指定时区, ex: Asia/Shanghai

        // 2025-07-30T00:32:32.876
        System.out.println(localDateTime); //未指定时区 localDateTime 不包含时区信息

        // localDateTime 指定 ZoneId 后变成 ZoneDateTime 就可位移
        ZonedDateTime zonedSys = localDateTime.atZone(ZoneId.systemDefault());

        // 使用ZoneDateTime 位移 到 UTC 後，觀察它們的小時部分，發現UTC 真的少了8小時
        ZonedDateTime utcZone = zonedSys.withZoneSameInstant(ZoneId.of("UTC")); //ZoneDateTime 位移 到 UTC
        System.out.println("位移 到 UTC = " + utcZone); // 位移 到 UTC = 2025-07-29T16:38:14.026Z[UTC]

        // 不管时区怎么转换, timestamp时间戳不会变
        System.out.println("位移 到 UTC, 转为 timestamp = " + utcZone.toInstant().toEpochMilli()); // long
    }
}
