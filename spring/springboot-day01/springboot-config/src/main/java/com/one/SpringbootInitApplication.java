package com.one;

import com.one.config.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootInitApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootInitApplication.class, args);
        // 根据名称获取bean对象
//        Object person2 = context.getBean("person2");
//        System.out.println(person2);

        // 获取bean对象的名称
        String[] beanNames = context.getBeanNamesForType(Person.class);
        for (String beanName : beanNames) {
            System.out.println(beanName);
            // person-com.one.config.Person 使用@EnableConfigurationProperties创建的bean对象名称
        }
    }

}
