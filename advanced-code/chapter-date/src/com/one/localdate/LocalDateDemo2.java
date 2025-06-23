package com.one.localdate;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 *  需求 : 用 LocalDate类来创建本月的日历
 *              最后的输出格式:
 *              Mon Tus Wen Thu Fri Sar Sun
 *                  1   2   3   4   5   6
*                7  8   9   10  11  12  13
 *              14  15  16  17  18  19  20
 *              21  22  23  24  25  26  27
 *              28  29  30
 *
 */
public class LocalDateDemo2 {
    public static void main(String[] args) {
        /**
         *  不能直接调用构造器来创建 LocalDate类对象,要使用静态工厂方法来创建对象
         *  得到当天的日期
         */
        LocalDate localDate = LocalDate.now();
//        System.out.println("localDate = " + localDate); // 输出格式:  yyyy-MM-dd

        /**
         *  获取 localDate对象的月份和日期, 返回int类型
         */
        int year = localDate.getYear();           // 获取该localDate对象所属的年
        int month = localDate.getMonthValue();   // 以整型数返回该localDate对象所属的当月月份
        int today = localDate.getDayOfMonth();   // 以整数数返回该localDate对象所属的天


        /*
            将 localDate对象设置为这个月的第一天, 就是获取与 return new LocalDate (year,month,1)一样的结果
         */
//        localDate = localDate.minusDays(today-1);
        //获取当月第一天的另一种写法
        localDate = LocalDate.of(year,month,1);
        System.out.println(localDate);

        /*
            获取 date对象日期对应的星期数 , 即该月第一天对应的星期数
         */
        DayOfWeek weekDay = localDate.getDayOfWeek();
        System.out.println("weekDay = " + weekDay);  //获取该月第一天的星期数  2020年9月1日  TUESDAY
        /*
            将星期数转换成对应的整数 ; 规则: 1=MONDAY 2= TUESDAY .... 7=SUNDAY
         */
        int value = weekDay.getValue();  // 结果: value = 2
//        System.out.println("value = " + value);
//        System.out.println("Mon\tTue\tWen\tThu\tFri\tSar\tSun");
        System.out.printf("%4s","Mon");          //打印格式让没有字符串都占据四个字符长度
        System.out.printf("%4s","Tue");
        System.out.printf("%4s","Wen");
        System.out.printf("%4s","Thu");
        System.out.printf("%4s","Fri");
        System.out.printf("%4s","Sar");
        System.out.printf("%4s","Sun");
        System.out.println();
        for (int i = 1; i < value; i++) {
            System.out.printf("%4s","");
        }
        //主要是要熟悉 LocalDate类中的几个方法
        while(localDate.getMonthValue() == month){
            System.out.printf("%4s",localDate.getDayOfMonth());
            localDate = localDate.plusDays(1);
            if(localDate.getDayOfWeek().getValue() == 1){  // 每到星期一 换行
                System.out.println();
            }
        }

    }
}
