package com.one.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

import java.util.Map;

/**
 * @ClassName: ClassCondition
 * @Description: Condition接口的实现类,用于对@Conditional判断注解进行赋值,给出判断条件
 * @Author: one
 * @Date: 2020/12/18
 */
public class ClassCondition implements Condition {
    /**
     * @param conditionContext 上下文对象,用于获取配置环境,IOC容器, ClassLoader对象
     * @param metadata 元数据对象(即注解), 用于获取注解中定义的属性值
     * @return 该方法返回true则创建Bean对象,返回false则不创建Bean对象
     */
//    @Override
//    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
//        // 在pom文件中导入了jedis坐标返回true,没有返回flase
//        boolean flag = true;
//        try {
//            Class clazz = Class.forName("redis.clients.jedis.Jedis"); // 存在硬编码问题
//        } catch (ClassNotFoundException e) { // 环境中没有jedis坐标,抛这个异常
//            flag = false;
//        }
//        return flag;
//    }

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        //通过注解传入的属性值判断是否创建Bean对象, 获取注解中的成员变量值(属性)
        Map<String, Object> map = metadata.getAnnotationAttributes(ConditionOnClass.class.getName());
        // 输出:{value=[redis.clients.jedis.Jedis]}
        System.out.println(map);
        // 将Object强转为String[]
        String[] value = (String[]) map.get("value");

        //遍历数组
        boolean flag = true;
        for (String className : value) {
            try {
                Class<?> clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                flag = false;
            }
        }
        return flag;
    }
}
