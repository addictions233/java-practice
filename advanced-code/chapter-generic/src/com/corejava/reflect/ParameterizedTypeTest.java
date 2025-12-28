package com.corejava.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ParameterizedTypeTest {

    /**
     * field字段的实际类型是java.util.List
     * field字段的泛型类型是java.util.List<java.lang.String>
     * field字段的泛型参数是java.lang.String
     */
    private List<String> field;

    public static void main(String[] args) throws NoSuchFieldException {
        Field field = ParameterizedTypeTest.class.getDeclaredField("field");

        // 此处获取到字段的实际Class类型
        Class<?> clazzType = field.getType();
        System.out.println("Field Type: " + clazzType); // 输出字段的实际Class类型: java.util.List

        // 此次获取到字段的泛型类型
        Type genericType = field.getGenericType();
        System.out.println("Field Generic Type: " + genericType); // 输出字段的泛型类型: java.util.List<java.lang.String>
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            // 获取参数化类型中的TypeVariable参数类型, 此处获取的是java.lang.String
            Type[] typeArguments = parameterizedType.getActualTypeArguments(); // 获取泛型参数
            for (Type t : typeArguments) {
                System.out.println(t); // 输出泛型参数类型: java.lang.String
            }

            // 获取当前Type所属的Type
            Type ownerType = parameterizedType.getOwnerType();
            System.out.println(ownerType); // 输出所属类型: null

            // 获取当前Type的Class原始类型
            Type rawType = parameterizedType.getRawType();
            System.out.println(rawType); // 输出原始类型: java.util.List
        }
    }
}
