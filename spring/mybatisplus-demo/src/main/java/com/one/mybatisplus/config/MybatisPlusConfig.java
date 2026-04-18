package com.one.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.one.mybatisplus.handler.MyDataPermissionHandler;
import com.one.mybatisplus.handler.MyTenantLineHandler;
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

        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        // 添加自定义插件
        interceptor.addInnerInterceptor(new ContextParamInterceptor());

        // 添加多租户插件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new MyTenantLineHandler()));

        // 添加数据权限插件
        interceptor.addInnerInterceptor(new DataPermissionInterceptor(new MyDataPermissionHandler()));

        return interceptor;
    }

}
