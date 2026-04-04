package com.one.enumdemo;

/**
 * @ClassName: EnumDemo
 * @Description: 测试枚举类的使用
 * @Author: one
 * @Date: 2021/12/26
 */
public class EnumDemo {
    public static void main(String[] args) {
        Season spring = Season.SPRING;
        // 枚举项的本质就是枚举类的实例对象
        System.out.println(spring.getClass());

        int[] months = spring.getMonths();
        for (int month : months) {
            System.out.println(month);
        }

        // 枚举类的values()方法， 获取所有的枚举项
        Season[] values = Season.values();
        for (Season value : values) {
            System.out.println(value);
        }

        // 通过枚举项的名称获取枚举项
        Season summer = Season.valueOf("SUMMER");
        System.out.println(summer);

        // 通过枚举项获取枚举项的名称
        String name = Season.SPRING.name();
        System.out.println(name);

        Season winter = Enum.valueOf(Season.class, "WINTER");
        System.out.println(winter);
    }
}
