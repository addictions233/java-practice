package com.one.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author one
 *      在mybatis plus中优雅的使用枚举, 枚举对象都是枚举类的final修饰的常量对象,不可变
 *      如果表中的value字段设置为tinyInt(1), mybatis plus会自动转换为Boolean类型
 *      如果想要转换为枚举类型: 必须将字段的数据类型由tinyInt(1)改为tinyInt(4)
 */
public enum UserStatusEnum implements IEnum<Integer> {
    /**
     * 0表示已审核, 1表示未审核, 2表示审核未通过
     */
    EXAMINED(0,"已审核"),
    EXAMINING(1,"未审核"),
    NOT_PASS_EXAMINING(2,"未通过审核");
    /**
     * mybatis plus的注解,指定枚举类和字段的对应值,效果等同于实现IEnum<Integer>接口
     */
    @EnumValue
    private final Integer value;

    /**
     * name属性
     */
    private final String name;

    /**
     * 枚举类的构造器是不能被外界调用的,反射也不能调用
     * @param value 属性
     * @param name 属性
     */
    UserStatusEnum(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
