package com.one.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: SpringConfig
 * @Description: spring的核心配置加载类
 * @Author: one
 * @Date: 2020/12/02
 */

@Configuration    //表明当前类为spring的配置类
@ComponentScan("com.one")  //指定spring在初始化容器时要扫描的包,指定纯注解方式下扫描的包路径(该路径下包含有所有注解的Bean类)
@PropertySource("classpath:jdbc.properties")  //引入资源文件
@Import({JDBCConfig.class,MyBatisConfig.class}) //引入第三方Bean作为被spring控制的资源
public class SpringConfig {
}
