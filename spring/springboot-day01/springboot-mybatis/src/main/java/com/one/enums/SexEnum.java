package com.one.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @ClassName: SexEnum
 * @Description: 性别枚举类
 * @Author: one
 * @Date: 2022/04/14
 */
public enum SexEnum {
    // 男
    MAN(0, "男"),
    // 女
    WOMEN(1, "女"),
    // 未知
    UNKNOWN(2, "未知");

    private Integer value;

    private String name;

    SexEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * jackson的注解,用于字段或者get方法上,用做该类型转换为json的取值方式
     * 如果枚举类型不指定转换为json的取值方式,默认去对象名称,例如: MAN
     */
    @JsonValue
    public String getName() {
        return this.name;
    }

    public Integer getValue() {
        return this.value;
    }

    /**
     * 通过value值获取SexEnum枚举对象
     * @param value 值
     * @return 枚举对象
     */
    public static SexEnum value2Object(Integer value) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if  (sexEnum.value.equals(value)) {
                return sexEnum;
            }
        }
        throw new IllegalArgumentException("illegal enum value:" + value);
    }


    @Override
    public String toString() {
        return this.name; // 重写toString()方法之后: 转换为json则是: 男
    }
}
