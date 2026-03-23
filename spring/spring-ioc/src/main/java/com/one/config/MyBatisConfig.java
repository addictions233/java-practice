package com.one.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @ClassName: MyBatisConfig
 * @Description: 实现mybatis的配置类
 * @Author: one
 * @Date: 2020/12/02
 */
public class MyBatisConfig {

    /**
     * 需要将 SqlSessionFactoryBean 交给spring 管理, 用来创建 sqlSession 的单例对象
     * @param dataSource 数据源
     * @return SqlSessionFactoryBean
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Autowired DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setTypeAliasesPackage("com.one.domain");
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    /**
     * 用来替代 @MapperScan 注解的作用
     * @return 进行mapper接口扫描
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        // 配置扫描的包路径
        msc.setBasePackage("com.one.dao");
        // 开启mapper层接口的注解扫描
//        msc.setAnnotationClass(Mapper.class); // 如果开启, mapper层接口必须添加mapper接口
        return msc;
    }
}
