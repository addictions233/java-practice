package com.one.enable.contextinitializer;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 * 使用spring.factories配置文件中的ApplicationContextInitializer来创建Bean对象
 * 使用@Enable*注解来创建Bean让spring管理
 * 使用 TypeExcludeFilter 来排除bean不让spring管理
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<AnnotationConfigServletWebServerApplicationContext> {


    /**
     * TypeExcludeFilter 需要在扫描bean之前就注入到IOC容器, 所以需要借助 ApplicationContextInitializer
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(AnnotationConfigServletWebServerApplicationContext applicationContext) {
        applicationContext.getBeanFactory().registerSingleton("myTypeExcludeFilter", new TypeExcludeFilter(){

            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return metadataReader.getClassMetadata().getClassName().equals(UserController.class.getName());
            }
        });
    }
}
