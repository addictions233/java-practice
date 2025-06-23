package com.one.localdate;
/**
 *  corejava给出的打印当月日历的标准代码
 *      注意: LocalDate类的创建对象是调用静态工厂方法,而不是直接调用构造方法
 */

import java.time.DayOfWeek;
import java.time.LocalDate;

public class LocalDateDemo3 {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        date = date.minusDays(today-1);
        DayOfWeek weekday = date.getDayOfWeek();
        int value = weekday.getValue();

        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (int i = 1; i < value; i++) {
            System.out.print("    ");
        }
        while (date.getMonthValue()==month){  // 判断是否还是本月
            System.out.printf("%3d",date.getDayOfMonth());
            if(date.getDayOfMonth() == today){
                System.out.print("*");
            } else{
                System.out.print(" ");
            }
            date = date.plusDays(1); //date像后移一天
            if(date.getDayOfWeek().getValue() == 1){ //星期数等于1 就换行
                System.out.println();
            }
        }
        if(date.getDayOfWeek().getValue()!= 1){
            System.out.println();
        }
    }
}
