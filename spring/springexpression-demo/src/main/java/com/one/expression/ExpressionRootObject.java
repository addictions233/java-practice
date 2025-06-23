package com.one.expression;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author one
 * 缓存表达式的根对象（spEl支持的语法都在这个里边）
 */
@Getter
@Setter
public class ExpressionRootObject {

    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;
    /**
     * 缓存字段，防止重复反射
     */
    private final Map<String, Object> fieldsCache = new HashMap<>(2);

    public ExpressionRootObject(Method method, Object[] args, Object target, Class<?> targetClass) {
        this.method = method;
        this.args = args;
        this.target = target;
        this.targetClass = targetClass;
    }

    /**
     * 获取指定变量字段的值
     *
     * @param fieldName 字段名称
     * @return 变量的值
     * @throws NoSuchFieldException   没有这个字段
     * @throws IllegalAccessException 不能访问
     */
    public Object fieldValue(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (fieldsCache.containsKey(fieldName)) {
            return fieldsCache.get(fieldName);
        }
        Field field = this.getTargetClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object fieldValue = field.get(this.target);
        fieldsCache.put(fieldName, fieldValue);
        return fieldValue;
    }

    /**
     * 获取唯一的方法(方法名称必须唯一)
     */
    public Method getUniqueMethod(String methodName) throws NoSuchMethodException {
        for (Method targetClassMethod : this.targetClass.getMethods()) {
            if (methodName.equals(targetClassMethod.getName())) {
                return targetClassMethod;
            }
        }
        throw new IllegalArgumentException("方法名称不存在");
    }

    /**
     * 获取指定方法
     */
    public Object getMethod(String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        return this.target.getClass().getMethod(methodName, parameterTypes);
    }

    /**
     * 得到方法名称
     */
    public String getMethodName() {
        return this.method.getName();
    }
}
