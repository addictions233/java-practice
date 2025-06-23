package com.one.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author one
 * 		mybatis plus简化了单表的查询,对于单表查询不写sql,采用面向对象的方式写查询语句
 */
@MapperScan("com.one.**.mapper")
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
