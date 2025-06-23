package com.one.enable;

import com.one.config.EnableUser;
import com.one.config.MyImportBeanDefinitionRegistrar;
import com.one.config.MyImportSelector;
import com.one.config.UserAutoConfig;
import com.one.domain.Role;
import com.one.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: EnableApplication
 * 如果获取第三方jar中定义的bean对象并放入IOC容器中:
 *      @ComponentScan注解: 包扫描的范围: 扫描当前引导类SpringBootApplication所属的包及其子包,其他第三方jar包中的内容则扫描不到
 *      @Import注解: 可以用@Import注解加载类 这些类都会被Spring创建bean对象,放在ioc容器中
 *      @Enable*注解: 用于自动开启某些功能,本质上是对@Import注解的封装,目的就是创建第三jar包中的Bean对象,放在IOC容器中
 * 测试如果获取Springboot-enable-other模块中(第三方jar包)定义的User的bean对象?
 *     使用注解@Import注解创建Bean对象的四种使用方式:
 *          1, 导入bean,直接创建其属性中对应的类对应的bean对象
 *          2, 导入配置类,而配置类中用@Bean注解标识了bean对象,且配置类中可以省略@Configuration注解
 *          3, 导入ImportSelector接口的实现类,该实现类必须重写selectImports方法,该方法返回一个全限定类名的数组,
 *              会创建这些全限定类名对应的bean对象
 *          4, 导入ImportBeanDefinitionRegistrar接口的实现类
 */
@SpringBootApplication
//@SpringBootApplication(exclude = UserAutoConfig.class)  // 此处的exclude必须排除的是spring.factory中声明的自动配置类,exclude和excludeName 只能排除auto-configuration类型的类，没法去排除自定义的类
@ComponentScan(basePackages = {"com.one.config"},excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {UserAutoConfig.class})})  // 1,第一种方式: 将第三方创建bean对象的类路径添加到@ComponentScan包扫描中

//@Import(User.class)   // 2, 第二种方式: 使用@Import注解主动加载第三方类,并创建其对应的bean对象
//@Import(UserConfig.class) // 这两种情况原理是一样的, 只适合导入单个的Bean对象,使用不方便, SpringBoot框架没有这么使用

//@EnableUser // 3,第三种方式:使用@Enable*注解开启某些功能,因为使用了@Import注解修饰了@EnableUser注解,所以本质上是@Import注解生效

//@Import(MyImportSelector.class) //4,第四种方式: 注入ImportSelector接口的实现类对象,批量的创建bean对象,SpringBoot框架使用的就是这种方式,读取Spring.factories文件中的配置类

//@Import(MyImportBeanDefinitionRegistrar.class) // 5,第五种方式: 注入ImportBeanDefinitionRegistrar接口的实现类对象
public class SpringBootEnableApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootEnableApplication.class, args);
//        Object bean = context.getBean("user");
//        System.out.println(bean);
        // 直接使用 @Import注解创建的Bean对象, 我们不知道该bean对象的名称,所以我们无法通过名称获取bean对象,要通过类型获取
        User user = context.getBean(User.class);
        System.out.println(user);
        // 通过bean对象的类型直接获取bean对象的名称
        String[] beanNamesForType = context.getBeanNamesForType(User.class);
        for (String beanName : beanNamesForType) {
            // 输出: com.one.domain.User
            System.out.println(beanName);
        }

//        Role role = context.getBean(Role.class);
//        System.out.println(role);
//        // 获取bean对象的名称(即id)
//        String[] beanNamesForType1 = context.getBeanNamesForType(Role.class);
//        for (String s : beanNamesForType1) {
//            System.out.println(s);
//        }

    }

    /**
     * 第三方jar包中Springboot-enable-other中已经定义了user的bean对象,
     * 如果我们按类型去注入会报错:beans.factory.NoUniqueBeanDefinitionException
     *
     * @return bean
     */
//    @Primary // 同类型的多个bean对象,使用@Primary会优先注入使用
    @Bean(name = "user2")
    public User user() {
        return new User();
    }


}
