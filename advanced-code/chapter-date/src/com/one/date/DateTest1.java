package com.one.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author one
 *  功能:
 *      计算一个人从出生到当前时间一共过了多少天
 *  如果输入的时间格式与要求的解析时间不同会抛出:
 *       java.text.ParseException: Unparseable date  //解析异常
 */
public class DateTest1 {
    public static void main(String[] args) throws ParseException {
        //创建键盘录入对象
        Scanner sc = new Scanner(System.in);
        // 请输入你的出生年月日
        System.out.println("请输入您的出生年月日:yyyy年MM月dd日 HH:mm:ss");
        String str = sc.nextLine();  // 字符串时间
        //创建时间解析格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //用时间解析格式对象解析字符串时间返回一个Date对象
        Date birthDate = sdf.parse(str);
        //获取birthDate对象距离时间原点的毫秒值
        long birthtime = birthDate.getTime();
        //获取现在时间距离时间原点的毫秒值
        long currentTime = System.currentTimeMillis();
        //将时间毫秒值转换为天数
        System.out.println((currentTime-birthtime)/(1000L*24*60*60));
    }
}
