package com.one.field;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Teacher teacher = new Teacher();
        Class clazz = teacher.getClass();

        // 获取属性
        Field field = clazz.getDeclaredField("salary");
        field.setAccessible(true);

        // 获取属性类型
        Class<?> type = field.getType();
        System.out.println("属性类型:" + type);  // 属性类型:double

        // 获取属性名称
        String name = field.getName();
        System.out.println("属性名称:" + name);  // 属性名称:salary

        // 获取属性修饰符, 参考java.lang.reflect.Modifier类
        int modifiers = field.getModifiers();
        System.out.println("属性修饰符:" + modifiers); // 属性修饰符:2
        System.out.println("属性修饰符:" + Modifier.toString(modifiers)); // 属性修饰符:private

        // 通过field获取属性值
        Object value = field.get(teacher);
        System.out.println("value = " + value);  // 输出: value = 0.0
        // 通过field设置属性值
        field.set(teacher,50000);
        Object value2 = field.get(teacher);
        System.out.println("value2 = " + value2); // 输出: value2 = 50000.0

    }
}
