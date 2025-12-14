package com.one.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MybatisPlusConfig
 * @Description: MybatisPlus配置类
 * @Author: one
 * @Date: 2025/12/14
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件, 注意和 pagehelper 的分页插件区分
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new ContextParamInterceptor());
        return interceptor;
    }

}
