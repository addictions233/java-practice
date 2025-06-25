package com.one.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: TestDruid
 * @Description: TODO
 * @Author: one
 * @Date: 2020/12/01
 */
public class TestDruid {
    @Test
    public void testDruid(){
        //2,加载核心配置文件
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //3,获取Bean对象, 注意: getBean()方法的参数为配置的<bean id="" 标签的id属性值
        DruidDataSource dataSource = (DruidDataSource) ctx.getBean("dataSource");
        //4,在控制台打印dataSource对象
        System.out.println(dataSource);


    }
}
