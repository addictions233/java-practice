package com.one.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @ClassName: PropertiesCondition
 * @Description: 使用@Conditional条件注解, 必须有一个类实现Condition接口作为判断条件
 * @Author: one
 * @Date: 2022/04/06
 */
public class PropertyCondition implements Condition {
//    @Override
//    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        // goods.name这里写死了,存在硬编码问题, 最好抽取为注解的属性值
//        String goodsName = context.getEnvironment().getProperty("goods.name");
//        if (StringUtils.isEmpty(goodsName)) {
//            return false;
//        }
//        // 配置文件中有goods.name就返回true,否则返回false
//        return true;
//    }

    /**
     * @param context 上下文对象,用于获取配置环境,IOC容器, ClassLoader对象
     * @param metadata 元数据对象(即注解), 用于获取注解中定义的属性值
     * @return 该方法返回true则创建Bean对象,返回false则不创建Bean对象
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 根据注解名称,获取注解中赋的value属性值
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionOnProperty.class.getName());
        String value = (String)attributes.get("value");
        // 根据value属性值,去看配置文件中是否存在其对应的值
        String property = context.getEnvironment().getProperty(value);
        if (StringUtils.isEmpty(property)) {
            return false;  // 如果配置文件中没有配置,就返回false,不创建bean对象
        }
        return true; // 如果配置文件中有配置,就返回true,创建bean对象
    }
}
