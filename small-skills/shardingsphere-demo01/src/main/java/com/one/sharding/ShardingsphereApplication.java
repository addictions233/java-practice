package com.one.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author one
 * 一,分库分表的几种方案:
 *    客户端:
 *          1, 在Spring的DAO层, 实现AbstractRoutingDatasource接口,然后使用AOP注解在DAO层实现数据源的动态切换
 *          2, 在ORM框架层, mybatis和hibernate都可以通过插件实现数据源的动态切换
 *          3, 在JDBC层, ORM框架本身就是对象JDBC的封装, 而sharding-jdbc实现分库分表方案就是在JDBC层级
 *     代理端:
 *          4, 独立运行的代理服务, MyCat和 Sharding Proxy就是在代理端实现分库分表
 *     服务端:
 *          5, 某些特定的数据源支持分表方案, 向MySql的merge引擎
 * 二, 用Sharding jdbc有三种配置方式:
 *          1, 编写java类, 获取shardingDataSource
 *          2, 使用yaml或者properties等配置文件的配置方式
 *          3, 使用Spring namespace命名空间的配置方式
 *
 */
@SpringBootApplication()
@MapperScan("com.one.sharding.mapper")
//集成cosid的springbootstarter需要声明这两个注解。其实应该放到框架里面去声明的，不应该交给应用声明。
//@EnableConfigurationProperties({MachineProperties.class})
//@ComponentScans(value = {@ComponentScan("me.ahoo.cosid")})
public class ShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereApplication.class, args);
    }

}
