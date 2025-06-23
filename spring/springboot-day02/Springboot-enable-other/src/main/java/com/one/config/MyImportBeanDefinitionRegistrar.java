package com.one.config;

import com.one.domain.Role;
import com.one.domain.User;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @ClassName: MyImportBeanDefinitionRegistrar 手动加载bean对象
 * @Description: @Import注解的第四种用法: 导入ImportBeanDefinitionRegistrar接口的实现类
 * @Author: one
 * @Date: 2021/04/05
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 手动加载User的bean对象
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(User.class).getBeanDefinition();
        // 参数1 String beanName: bean对象的名称id
        // 参数2 BeanDefinition beanDefinition: bean对象的定义
        registry.registerBeanDefinition("user",beanDefinition); // 将Bean对象注册到IOC容器中,并取名字
        // 手动加载Role的bean对象
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.rootBeanDefinition(Role.class).getBeanDefinition();
        registry.registerBeanDefinition("role",beanDefinition1);


    }
}
