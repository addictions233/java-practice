package com.one.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: MyImportSelector
 * @Description: @Import注解的第三种用法: 导入ImportSelector接口的实现类,重写selectImports,该方法返回一个字符串数组,
 * 数组的内容是全类名,这样会自动创建这些全类名对应的bean对象,放在IOC容器中
 * @Author: one
 * @Date: 2021/04/05
 */
public class MyImportSelector implements ImportSelector {
    /**
     * 重写ImportSelector接口的方法
     * @param metaData 元数据,可以获取注解中的属性值
     * @return ioc容器会创建当前方法返回的所有类名的bean对象, String[]中存储全限定类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata metaData) {
        // 这里是类的全限定名是写死的,存在硬编码问题
        // SpringBoot工程中是从Spring.factories配置文件中读取所有的配置类的全限定类名,所以spring.factories中的配置类都会加载
        // 虽然spring.factories中的配置类都会加载,但是最终是否创建bean对象还是有@Condition*注解中的条件决定的
        return new String[]{"com.one.config.UserAutoConfig"};
//        return new String[]{"com.one.domain.User", "com.one.domain.Role"};
    }
}
