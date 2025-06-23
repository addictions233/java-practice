package com.one.simpledateformate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author one
 * SimpleDateFormat类: 为了方便的创建任意时间的Date对象,我们可以使用SimpleDateFormat类
 * 构造方法:
 * String pattern : 时间日期格式化代码
 * 时间格式: yyyy:MM:dd HH:mm:ss
 * 解析时间字符串的方法:
 * Date parse(String time)
 */
public class SimpleDateFormatDemo1 {
    public static void main(String[] args) throws ParseException {
        //创建字符串时间
        String strDate = "1990年01月01日 00:00:00";
        String strDate2 = "2000-12-12 12:00:00";

        //创建 SimpleDateFormat类对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = sdf.parse(strDate);
        Date parse = sdf2.parse(strDate2);

        System.out.println(date);
        System.out.println(parse);

    }
}
