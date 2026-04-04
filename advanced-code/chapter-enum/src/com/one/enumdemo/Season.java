package com.one.enumdemo;

/**
 * 枚举本质就是一个java类， 所有的枚举成员都是枚举类的一个public static final修饰的成员对象，
 * 枚举成员的名称就是对象变量名，常量都是大写，所以枚举成员的名称也是大写
 *
 * 所有的枚举列都是 Enum类的子类，所以所有的枚举类都能使用Enum类中的public 默认 protected修饰的方法
 * @author one
 */
public enum Season {
    /**
     * 枚举项本质就是常量的枚举类对象，枚举项的名称就是对象的变量名称
     */
    SPRING(new int[] {1,2,3}),
    SUMMER(new int[] {4,5,6}),
    AUTUMN(new int[] {7,8,9}),
    WINTER(new int[] {10,11,12});

    /**
     *  成员变量
     */
    private int[] months;

    /**
     * 私用枚举类的构造器, 防止其它类创建枚举对象（枚举项）
     * @param months
     */
    private Season(int[] months) {
        this.months = months;
    }

    public int[] getMonths() {
        return this.months;
    }
}
