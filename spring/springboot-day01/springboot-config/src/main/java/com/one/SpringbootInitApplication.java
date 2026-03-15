package com.one;

import com.one.config.Person;
import com.one.config.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableConfigurationProperties({Person.class, User.class})
public class SpringbootInitApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringbootInitApplication.class);

        // 设置默认配置, 优先级是最低的
        Map<String, Object> map = new HashMap<>();
        map.put("server.port", 8086);
        springApplication.setDefaultProperties(map);

        ConfigurableApplicationContext context = springApplication.run(args);

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
