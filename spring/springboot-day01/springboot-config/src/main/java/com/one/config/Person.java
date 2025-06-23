package com.one.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName: PersonConfig
 * @Description: 使用 @ConfigurationProperties注解读取配置文件中的值,给Bean对象属性赋值,如果没有Bean对象,使用这个注解就没有意义
 *                          本质上和@Value注解一样,都是调用set方法给属性赋值
 *  注意: 如果Person类上不用加@Component注解,是不会创建Bean对象的,需要结合其它方式才会创建bean对象
 *          1, 结合@Component注解创建bean对象
 *          2, 结合@EnableConfigurtionProperties注解创建bean对象
 *          3, 修饰方法, 结合@Configuration+@Bean注解一起使用创建Bean对象
 * @Author: one
 * @Date: 2021/04/06
 */
@Data
@ConfigurationProperties(prefix = "person")
@Primary //有多个同类型的Bean对象创建(bean对象可以类型相同,但是不能name相同),使用@Autowired按照类型注入就会冲突,需要使用@Primary注解分主次
public class Person {
    private String name;
    private Integer age;
}
