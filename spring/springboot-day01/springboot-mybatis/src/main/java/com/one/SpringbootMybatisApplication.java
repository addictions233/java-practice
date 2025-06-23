package com.one;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author one
 * 注意: 项目所有的包必须在启动类的平级或者子级的位置
 */
@SpringBootApplication
@EnableAsync // 开启异步支持
@EnableTransactionManagement // 开启事务支持
public class SpringbootMybatisApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
    }

}
